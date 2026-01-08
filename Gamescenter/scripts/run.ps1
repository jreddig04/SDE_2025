$ErrorActionPreference = "Stop"

function Ensure-Dir($p){
    if (-not (Test-Path $p)) {
        New-Item -ItemType Directory -Path $p | Out-Null
    }
}

function Ensure-Java {
    if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
        Write-Error "java not found. Please install JDK 17+ and add it to your PATH."
        exit 1
    }
    if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
        Write-Error "javac not found. Please install JDK 17+ and add it to your PATH."
        exit 1
    }
}

function Ensure-JUnit {
    Ensure-Dir "lib"
    $ver = "1.10.2"
    $jar = "junit-platform-console-standalone-$ver.jar"
    $dest = Join-Path "lib" $jar
    if (-not (Test-Path $dest)) {
        $url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$ver/$jar"
        Write-Host "Downloading $jar ..."
        Invoke-WebRequest -Uri $url -OutFile $dest
    }
    return $dest
}

function Get-MavenCmd {
    if (Test-Path ".\mvnw.cmd") { return ".\mvnw.cmd" }
    if (Get-Command mvn -ErrorAction SilentlyContinue) { return "mvn" }
    return $null
}

function Is-MavenProject {
    return (Test-Path ".\pom.xml")
}

function Compile-All($junitJarLeaf) {
    $mvn = Get-MavenCmd
    if (Is-MavenProject -and $mvn) {
        & $mvn -q test-compile
        return
    }

    Ensure-Dir "out/main"
    Ensure-Dir "out/test"

    $mainSources = Get-ChildItem -Path "src/main/java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
    if ($mainSources.Count -gt 0) {
        & javac -encoding UTF-8 -d "out/main" $mainSources
    }

    $testSources = Get-ChildItem -Path "src/test/java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
    if ($testSources.Count -gt 0) {
        $cp = "out/main;lib/$junitJarLeaf"
        & javac -encoding UTF-8 -cp $cp -d "out/test" $testSources
    }
}

function Run-Tests($junitJarLeaf) {
    $mvn = Get-MavenCmd
    if (Is-MavenProject -and $mvn) {
        Write-Host "`n=== RUNNING TESTS ==="
        & $mvn test
        Write-Host "=== TESTS DONE (exit $LASTEXITCODE) ===`n"
        return
    }


    if (-not (Test-Path "out/test")) { return }
    $cp = "out/main;out/test"
    & java -jar "lib/$junitJarLeaf" --classpath $cp --scan-class-path
}

function Run-App {
    $mvn = Get-MavenCmd
    if (Is-MavenProject -and $mvn) {
        & $mvn spring-boot:run
        return
    }

    & java -cp "out/main" arcade.cli.menu.Main
}

Ensure-Java
$junitJarPath = Ensure-JUnit
$junitJarLeaf = Split-Path $junitJarPath -Leaf
Compile-All $junitJarLeaf
Run-Tests $junitJarLeaf
Run-App
