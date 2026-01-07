$ErrorActionPreference = "Stop"

function Ensure-Dir($p)
{
    if (-not (Test-Path $p))
    {
        New-Item -ItemType Directory -Path $p | Out-Null
    }
}

function Ensure-Java
{
    if (-not (Get-Command javac -ErrorAction SilentlyContinue))
    {
        Write-Error "javac not found. Please install JDK 17+ and add it to your PATH."
    }
}

function Ensure-JUnit
{
    Ensure-Dir "lib"
    $ver = "1.10.2"
    $jar = "junit-platform-console-standalone-$ver.jar"
    $dest = Join-Path "lib" $jar
    if (-not (Test-Path $dest))
    {
        $url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$ver/$jar"
        Write-Host "Downloading $jar ..."
        Invoke-WebRequest -Uri $url -OutFile $dest
    }
    return $dest
}

function Compile-All($junitJar)
{
    Ensure-Dir "out/main"
    Ensure-Dir "out/test"

    $mainSources = Get-ChildItem -Path "src/main/java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
    if ($mainSources.Count -gt 0)
    {
        & javac -encoding UTF-8 -d "out/main" $mainSources
    }

    $testSources = Get-ChildItem -Path "src/test/java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
    if ($testSources.Count -gt 0)
    {
        $cp = "out/main;lib/$junitJar"
        & javac -encoding UTF-8 -cp $cp -d "out/test" $testSources
    }
}

function Run-Tests($junitJar)
{
    if (-not (Test-Path "out/test"))
    {
        return
    }
    $cp = "out/main;out/test"
    & java -jar "lib/$junitJar" --classpath $cp --scan-class-path
}

function Run-App
{
    & java -cp "out/main" arcade.cli.Main
}


Ensure-Java
$junitJarPath = Ensure-JUnit
$junitJar = Split-Path $junitJarPath -Leaf
Compile-All $junitJar
Run-Tests $junitJar
Run-App
