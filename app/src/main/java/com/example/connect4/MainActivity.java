package com.example.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {
    int col1=0,col2=0,col3=0,col4=0,col5=0,col6=0,col7=0,turn =1,chances=0,start=0,startCount=0;
    int[][] grd = new int[6][7];
    ImageView[][] images = new ImageView[6][7];

    public void startGame(View view) {
        startCount++;
        destruct();
        EditText name1EditText = (EditText) findViewById(R.id.name1EditText);
        EditText name2EditText = (EditText) findViewById(R.id.name2EditText);
        if(name1EditText.getText().toString().isEmpty()||name2EditText.getText().toString().isEmpty())
            Toast.makeText(this,"Pls Enter the names first",Toast.LENGTH_SHORT).show();
        else
        {
            start=1;
            if(startCount==0)
                Toast.makeText(this,"GAME STARTED!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"GAME STARTED AGAIN!",Toast.LENGTH_SHORT).show();
        }
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setText(R.string.restart);
    }
    public void destruct() {
        col1=0;
        col2=0;
        col3=0;
        col4=0;
        col5=0;
        col6=0;
        col7=0;
        turn =1;
        chances=0;
        start=0;
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                grd[i][j]=0;
                images[i][j].setAlpha(0f);
            }
        }
    }
    public int checkHorizontal(int[][] grid,int r) {
        int centre = grid[r][3];
        if(centre!=0&&((grid[r][0]==centre&&grid[r][1]==centre&&grid[r][2]==centre&&grid[r][3]==centre)||(grid[r][1]==centre&&grid[r][2]==centre&&grid[r][3]==centre&&grid[r][4]==centre)||(grid[r][2]==centre&&grid[r][3]==centre&&grid[r][4]==centre&&grid[r][5]==centre)||(grid[r][3]==centre&&grid[r][4]==centre&&grid[r][5]==centre&&grid[r][6]==centre)))
            return 1;
        else
            return 0;
    }
    public int checkVertical(int[][] grid,int c) {
        int centre = grid[3][c];
        if(centre!=0&&((grid[0][c]==centre&&grid[1][c]==centre&&grid[2][c]==centre&&grid[3][c]==centre)||(grid[1][c]==centre&&grid[2][c]==centre&&grid[3][c]==centre&&grid[4][c]==centre)||(grid[2][c]==centre&&grid[3][c]==centre&&grid[4][c]==centre&&grid[5][c]==centre)))
            return 1;
        else
            return 0;
    }
    public int checkSlant(int[][] grid, int r, int c) {
        int co,ro,last,count=0;
        boolean win=false;
        if(r+c<6)
            {co=0;ro=r+c;}
        else
            {co=r+c-5;ro=5;}
        last =grid[ro][co];
        ro--;co++;
        while(co>=0&&co<7&&ro<6&&ro>=0)
        {
            if(last==grid[ro][co]&&last!=0) {
                count++;
                if(count==3) {
                    win = true;
                    break;
                }
            }
            else
                count=0;
            last=grid[ro][co];
            ro--;
            co++;
        }
        if(r>=c)
        {co=0;ro=r-c;}
        else
        {co=c-r;ro=0;}
        last=grid[ro][co];
        count=0;
        ro++;co++;
        Log.i("TAG", "CHECK1");
        while(co<7&&co>=0&&ro>=0&&ro<6&&!win)
        {
            Log.i("TAG", "CHECK2");
            if(last==grid[ro][co]&&last!=0) {
                count++;
                if(count==3) {
                    win = true;
                    break;
                }
            }
            else
                count=0;
            last=grid[ro][co];
            ro++;
            co++;
        }
        if(win)
            return 1;
        else
            return 0;
    }
    public int checkWin(int[][] grid,int r,int c) {
        if(checkHorizontal(grid, r)==1||checkSlant(grid, r, c)==1||checkVertical(grid, c)==1)
            return 1;
        else
            return 0;
    }

    public void onClick1(View view) throws InterruptedException {
        int al=images[5][0].getImageAlpha();
Log.i("TAG",Integer.toString(al));
        if(start==1) {
            if (col1 < 6) {
                float tim = 800 * (6 - (float) col1) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col1][0] = 1;
                    images[5 - col1][0].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col1][0].setTranslationY(-tim);
                    images[5 - col1][0].animate().translationYBy(tim - 10).alphaBy(1).setDuration((long) (1000 * sqrt((6 - col1)) / 6));
                } else {
                    images[5 - col1][0].setImageResource(R.drawable.yellow);
                    grd[5 - col1][0] = 2;
                    turn = 1;
                    images[5 - col1][0].setTranslationY(-tim);
                    images[5 - col1][0].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col1)) / 6));
                }
                col1++;
                if (checkWin(grd, 6 - col1, 0) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick2(View view) {
        if(start==1) {
            if (col2 < 6) {
                float tim = 800 * (6 - (float) col2) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col2][1] = 1;
                    images[5 - col2][1].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col2][1].setTranslationY(-tim);
                    images[5 - col2][1].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col2)) / 6));
                } else {
                    images[5 - col2][1].setImageResource(R.drawable.yellow);
                    grd[5 - col2][1] = 2;
                    turn = 1;
                    images[5 - col2][1].setTranslationY(-tim);
                    images[5 - col2][1].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col2)) / 6));
                }
                col2++;
                if (checkWin(grd, 6 - col2, 1) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick3(View view) {
        if(start==1) {
            if (col3 < 6) {
                float tim = 800 * (6 - (float) col3) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col3][2] = 1;
                    images[5 - col3][2].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col3][2].setTranslationY(-tim);
                    images[5 - col3][2].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col3)) / 6));
                } else {
                    images[5 - col3][2].setImageResource(R.drawable.yellow);
                    grd[5 - col3][2] = 2;
                    turn = 1;
                    images[5 - col3][2].setTranslationY(-tim);
                    images[5 - col3][2].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col3)) / 6));
                }
                col3++;
                if (checkWin(grd, 6 - col3, 2) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick4(View view) {
        if(start==1) {
            if (col4 < 6) {
                float tim = 800 * (6 - (float) col4) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col4][3] = 1;
                    images[5 - col4][3].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col4][3].setTranslationY(-tim);
                    images[5 - col4][3].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col4)) / 6));
                } else {
                    images[5 - col4][3].setImageResource(R.drawable.yellow);
                    grd[5 - col4][3] = 2;
                    turn = 1;
                    images[5 - col4][3].setTranslationY(-tim);
                    images[5 - col4][3].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col4)) / 6));
                }
                col4++;
                if (checkWin(grd, 6 - col4, 3) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick5(View view) {
        if(start==1) {
            if (col5 < 6) {
                float tim = 800 * (6 - (float) col5) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col5][4] = 1;
                    images[5 - col5][4].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col5][4].setTranslationY(-tim);
                    images[5 - col5][4].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col5)) / 6));
                } else {
                    images[5 - col5][4].setImageResource(R.drawable.yellow);
                    grd[5 - col5][4] = 2;
                    turn = 1;
                    images[5 - col5][4].setTranslationY(-tim);
                    images[5 - col5][4].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col5)) / 6));
                }
                col5++;
                if (checkWin(grd, 6 - col5, 4) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick6(View view) {
        if(start==1) {
            if (col6 < 6) {
                float tim = 800 * (6 - (float) col6) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col6][5] = 1;
                    images[5 - col6][5].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col6][5].setTranslationY(-tim);
                    images[5 - col6][5].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col6)) / 6));
                } else {
                    images[5 - col6][5].setImageResource(R.drawable.yellow);
                    grd[5 - col6][5] = 2;
                    turn = 1;
                    images[5 - col6][5].setTranslationY(-tim);
                    images[5 - col6][5].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col6)) / 6));
                }
                col6++;
                if (checkWin(grd, 6 - col6, 5) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick7(View view) {
        if(start==1) {
            if (col7 < 6) {
                float tim = 800 * (6 - (float) col7) / 6;
                chances++;
                if (turn == 1) {
                    grd[5 - col7][6] = 1;
                    images[5 - col7][6].setImageResource(R.drawable.red);
                    turn = 2;
                    images[5 - col7][6].setTranslationY(-tim);
                    images[5 - col7][6].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col7)) / 6));
                } else {
                    images[5 - col7][6].setImageResource(R.drawable.yellow);
                    grd[5 - col7][6] = 2;
                    turn = 1;
                    images[5 - col7][6].setTranslationY(-tim);
                    images[5 - col7][6].animate().translationYBy(tim - 10).alpha(1).setDuration((long) (1000 * sqrt((6 - col7)) / 6));
                }
                col7++;
                if (checkWin(grd, 6 - col7, 6) == 1) {
                    Button startButton = (Button) findViewById(R.id.startButton);
                    startButton.setText(R.string.start_again);
                    EditText nameEditText;
                    if (turn == 2)
                        nameEditText = (EditText) findViewById(R.id.name1EditText);
                    else
                        nameEditText = (EditText) findViewById(R.id.name2EditText);
                    Toast.makeText(this, "Winner is " + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    start=0;
                }
                if(chances==42&&start==1)
                {
                    Toast.makeText(this,"DRAW!!",Toast.LENGTH_SHORT).show();
                    start=0;
                }
            } else
                Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Pls Start the game first",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
    }
    public void initialise() {
        images[0][0] = (ImageView) findViewById(R.id.imageView1_1);
        images[0][1] = (ImageView) findViewById(R.id.imageView1_2);
        images[0][2] = (ImageView) findViewById(R.id.imageView1_3);
        images[0][3] = (ImageView) findViewById(R.id.imageView1_4);
        images[0][4] = (ImageView) findViewById(R.id.imageView1_5);
        images[0][5] = (ImageView) findViewById(R.id.imageView1_6);
        images[0][6] = (ImageView) findViewById(R.id.imageView1_7);

        images[1][0] = (ImageView) findViewById(R.id.imageView2_1);
        images[1][1] = (ImageView) findViewById(R.id.imageView2_2);
        images[1][2] = (ImageView) findViewById(R.id.imageView2_3);
        images[1][3] = (ImageView) findViewById(R.id.imageView2_4);
        images[1][4] = (ImageView) findViewById(R.id.imageView2_5);
        images[1][5] = (ImageView) findViewById(R.id.imageView2_6);
        images[1][6] = (ImageView) findViewById(R.id.imageView2_7);

        images[2][0] = (ImageView) findViewById(R.id.imageView3_1);
        images[2][1] = (ImageView) findViewById(R.id.imageView3_2);
        images[2][2] = (ImageView) findViewById(R.id.imageView3_3);
        images[2][3] = (ImageView) findViewById(R.id.imageView3_4);
        images[2][4] = (ImageView) findViewById(R.id.imageView3_5);
        images[2][5] = (ImageView) findViewById(R.id.imageView3_6);
        images[2][6] = (ImageView) findViewById(R.id.imageView3_7);

        images[3][0] = (ImageView) findViewById(R.id.imageView4_1);
        images[3][1] = (ImageView) findViewById(R.id.imageView4_2);
        images[3][2] = (ImageView) findViewById(R.id.imageView4_3);
        images[3][3] = (ImageView) findViewById(R.id.imageView4_4);
        images[3][4] = (ImageView) findViewById(R.id.imageView4_5);
        images[3][5] = (ImageView) findViewById(R.id.imageView4_6);
        images[3][6] = (ImageView) findViewById(R.id.imageView4_7);

        images[4][0] = (ImageView) findViewById(R.id.imageView5_1);
        images[4][1] = (ImageView) findViewById(R.id.imageView5_2);
        images[4][2] = (ImageView) findViewById(R.id.imageView5_3);
        images[4][3] = (ImageView) findViewById(R.id.imageView5_4);
        images[4][4] = (ImageView) findViewById(R.id.imageView5_5);
        images[4][5] = (ImageView) findViewById(R.id.imageView5_6);
        images[4][6] = (ImageView) findViewById(R.id.imageView5_7);

        images[5][0] = (ImageView) findViewById(R.id.imageView6_1);
        images[5][1] = (ImageView) findViewById(R.id.imageView6_2);
        images[5][2] = (ImageView) findViewById(R.id.imageView6_3);
        images[5][3] = (ImageView) findViewById(R.id.imageView6_4);
        images[5][4] = (ImageView) findViewById(R.id.imageView6_5);
        images[5][5] = (ImageView) findViewById(R.id.imageView6_6);
        images[5][6] = (ImageView) findViewById(R.id.imageView6_7);

        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++) {
                grd[i][j] = 0;
                images[i][j].setImageResource(R.drawable.white);
            }
        }
    }
}
