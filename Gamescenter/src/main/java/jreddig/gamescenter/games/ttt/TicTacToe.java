package jreddig.gamescenter.games.ttt;

import jreddig.gamescenter.core.Command;
import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameDescriptor;
import jreddig.gamescenter.core.GameObserver;

import java.util.Arrays;

public final class TicTacToe implements Game {
    private final char[][] b = new char[3][3];
    private char current = 'X';
    private String status = "RUNNING";
    private boolean finished = false;


    private GameObserver observer;
    public void setObserver(GameObserver obs) { this.observer = obs; }
    private void notifyObserver() { if (observer != null) observer.onStateChange(state()); }

    @Override public void init() { for (var r : b) Arrays.fill(r, ' '); }

    @Override public void handle(Command cmd) {
        if (finished) return;
        if (cmd instanceof TttPlace p && inBounds(p) && b[p.r()][p.c()] == ' ') {
            b[p.r()][p.c()] = current;
            if (win(current)) { status = current + " wins"; finished = true; }
            else if (full()) { status = "draw"; finished = true; }
            else current = (current == 'X') ? 'O' : 'X';
            notifyObserver();
        }
    }

    @Override public TttState state() { return new TttState(copy(), current, status); }
    @Override public boolean isFinished() { return finished; }
    @Override public GameDescriptor descriptor() {
        return new GameDescriptor("tictactoe", "Tic-Tac-Toe", "3x3 Klassiker");
    }

    private boolean inBounds(TttPlace p){ return p.r()>=0&&p.r()<3&&p.c()>=0&&p.c()<3; }
    private boolean full(){ for (var r: b) for (var c: r) if (c==' ') return false; return true; }
    private boolean win(char x){
        for(int i=0;i<3;i++) if(b[i][0]==x&&b[i][1]==x&&b[i][2]==x) return true;
        for(int i=0;i<3;i++) if(b[0][i]==x&&b[1][i]==x&&b[2][i]==x) return true;
        return (b[0][0]==x&&b[1][1]==x&&b[2][2]==x) || (b[0][2]==x&&b[1][1]==x&&b[2][0]==x);
    }
    private char[][] copy(){ var c=new char[3][3]; for(int i=0;i<3;i++) System.arraycopy(b[i],0,c[i],0,3); return c; }

    char boardAt(int r, int c) { return b[r][c]; }
}


