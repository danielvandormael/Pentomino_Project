package sample;

import java.util.ArrayList;

public class Bruijn{
    public static final double accuracy = 0.90;
    public static int [] value;
    public static int score;
    public static int empty_spot;
    public static int a;
    public static int b;
    public static int c;
    public static int[] abcs = new int[3];
    public static int HORIZONTALGRIDSIZE = 5;
    public static int VERTICALGRIDSIZE = 8;
    public static int DEPTHSIZE = 33;
    public static int [] nr ={24, 12, 24};
    public static String [] ab;
    public static int branches;
    public static boolean pentomino;
    public static long start;
    public static long end;
    public static int[][][] gridm;

    public static void Bruijns(int[][][] grid, int comp, reader re, int[] used){
        branches++;

        final int check = comp; //counts how many pieces are currently in the grid
        // System.out.println(used[0] + " " + used[1] + " " + used[2]);
        if (checkEnd(grid,comp, used)){ //if we placed enough pieces in the grid, the end is reached
            if (pentomino){
                end = System.currentTimeMillis();
                System.out.println("Solution found ");
                System.out.println("no perfect fit the best score of the box :"+score);
                System.out.println("L :"+used[0]+" T :"+used[1]+" P :"+used[2]);
                System.out.println(empty_spot);
                System.out.println(Math.round((end-start)/1000)+"s");
                gridm =grid.clone();
                UI.repaint(gridm, ab);
                System.exit(0);
            }else{
                System.out.println("Solution found ");
                System.out.println("no perfect fit the best score of the box :"+score);
                System.out.println("A :"+used[0]+" B :"+used[1]+" C :"+used[2]);
                System.out.println(empty_spot);
                System.out.println(Math.round((end-start)/1000)+"s");
                gridm =grid.clone();
                UI.repaint(gridm, ab);
                System.exit(0);
            }
        }
        else{
            int[] nexthole = getNextHole(grid); //get the next hole
            if(nexthole == null){
                return;
            }
            int x = nexthole[0];
            int y = nexthole[1];
            int z = nexthole[2];
            ArrayList<int[]> bestpieces = checkmax(x, y, z, used, grid, re); //returns an ArrayList of arrays with the best places to
            if(bestpieces.size() == 0) {

                //when there is no solution found to fill in this gap
                if(used[3] >= empty_spot) //we have a certain amount of gaps that are allowed, if our gapcount is still less than the number of empty spots allowed
                {
                    if (check==0){ //if we are completely back at the first time we called Bruijns:
                        System.out.println("Solution not found ");
                        return;
                    }
                    else {
                        return;
                    }
                }
                else {
                    int[] temused =copy(used); //temporary used pieces
                    temused[3]++;
                    int[][][] locgrid = copy(grid); //localgrid
                    locgrid[y][x][z] = -100;
                    //fill the gap in the grid with -100 so that it won't be chosen again as piece
                    Bruijns(locgrid, comp, re, temused);

                }
            } else{

                //Make the ArrayList into an Array
                int[][] bestpiecesinarray = new int[bestpieces.size()][6];
                bestpiecesinarray = bestpieces.toArray(bestpiecesinarray);
                int times = 0;
                for(int i = bestpiecesinarray.length-1; i>=0; i--) {
                    int[] bestpiece = bestpiecesinarray[i];
                    //clone the grid
                    int [][][] localgrid = copy(grid);

                    //retrieve piece
                    int[][][] piece = re.data(bestpiece[3], bestpiece[4]);

                    //make a temporary usedarray
                    int[] tempused = copy(used);

                    //put in the used array that we are using another piece
                    tempused[bestpiece[3]]++;

                    //place the piece in our localgrid
                    localgrid = actuallyPlace(bestpiece, piece, localgrid);

                    Bruijns(localgrid, ++comp, re, tempused);
                }
                if(used[3] >= empty_spot) {
                    if (check==0){ //if we are completely back at the first time we called Bruijns:

                    }
                    else {
                        return;
                    }
                }
                else {
                    int[] temused =copy(used); //temporary used pieces
                    temused[3]++;
                    int[][][] locgrid = copy(grid); //localgrid
                    locgrid[y][x][z] = -100;
                    //fill the gap in the grid with -100 so that it won't be chosen again as piece
                    Bruijns(locgrid, comp, re, temused);

                }

            }
            if (check==0){ //if we are completely back at the first time we called Bruijns:
                System.out.println("Solution not found "+score);
            }
            else {
                return;
            }
        }
    }


    public static ArrayList<int[]> checkmax(int x, int y, int z, int [] used, int[][][] grid, reader re) {
        ArrayList<int[]> coordinates = new ArrayList<int[]>(); //new arraylist that will have coordinates
        for(int id = 0; id<3; id++) {
            for (int r=0;r<nr[id];r++){
                if(abcs[id] > used[id]) { //for all ids and all rotations of the pieces
                    int [][][] piece = reader.data(id,r); //retrieve piece
                    ArrayList<int[]> checked = checkOverlap(grid, piece, x, y, z,id, r); //this method checks in what way we can place this piece to fill the hole
                    for(int i = 0; i<checked.size(); i++) {
                        if (checked.get(i)[6] == 1){ //5 will only get filled as 1 when an option is found
                            coordinates.add(checked.get(i)); //add the new option to the arraylist

                        }
                    }

                }
            }
        }
        return coordinates;
    }



    public static void all_poss(int [] poss){
        a =poss[0];
        b =poss [1];
        c =poss [2];
        abcs[0] = a;
        abcs[1] = b;
        abcs[2] = c;
        score = poss [3];
        empty_spot =(VERTICALGRIDSIZE*DEPTHSIZE*HORIZONTALGRIDSIZE) -poss[4];
        value = new int [a+b+c];
        for (int i =0;i<value.length;i++){
            if(i<a){
                value[i]=0;
            }
            else if (i<a+b){
                value[i]=1;
            }
            else if (i<a+b+c){
                value[i]=2;
            }
        }
        branches = 0;
        initializeGrid();
        // }
        // else{
        //     for (int i =witch;i>=0;i--){
        //         if (poss[i][3]!=0){
        // 			all_poss(i, fit);
        //         }
        //     }
        // }
    }

    public static void initializeGrid() { //initializes grid
        int[][][] grid = new int[VERTICALGRIDSIZE][HORIZONTALGRIDSIZE][DEPTHSIZE];
        for(int i = 0; i<VERTICALGRIDSIZE; i++) {
            for(int j = 0; j<HORIZONTALGRIDSIZE; j++) {
                for(int k = 0; k<DEPTHSIZE; k++) {
                    grid[i][j][k] = -1;
                }
            }
        }
        reader re;
        if (pentomino){
            re = new reader(5);
            nr [0]=24;
            nr [1]=12;
            nr [2]=24;
        }
        else{
            re = new reader(10);
            nr[0] =3;
            nr[1]=6;
            nr[2]=1;
        }
        int[] used = {0,0,0,0}; //how many times we used all our pieces
        Bruijns(grid, 0,re, used);
    }

    public static boolean checkEnd(int[][][] grid,int p,int [] used) {//if the number of placed pieces is the same as the number we have to place
        if((used[0]*3 + used[1]*4 + used[2]*5)>=(score*accuracy)){//if the score is in our range of accuracy the end is reached
            score = (used[0]*3 + used[1]*4 + used[2]*5);
            return true;
        }
        if (p!=value.length)return false;
        if (used[0]!=abcs[0]) return false;
        if (used[1]!=abcs[1]) return false;
        if (used[2]!=abcs[2]) return false;
        return true;
    }

    public static boolean answ(int [][][] grid){
        int area = HORIZONTALGRIDSIZE*VERTICALGRIDSIZE*DEPTHSIZE;
        int count =0;
        for (int w =0; w<value.length;w++){
            int id =value[w];
            int [][][]ptp =reader.data(0,1);
            for(int i = 0; i < ptp.length; i++){
                for (int j = 0; j < ptp[0].length; j++){
                    for (int k=0;k<ptp[0][0].length;k++){
                        if (ptp[i][j][k]==1){
                            count++;
                        }
                    }

                }
            }
        }
        if (count==area){
            return true;
        }
        return false;
    }
    //in this method we look at all the smaller cubes the pentominoes consist of and try to put this on the hole we are trying to fill
    public static ArrayList<int[]> checkOverlap(int[][][] grid, int[][][] piece, int x, int y, int z,int id, int r) {
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        for(int i = 0; i<piece.length && i<=(VERTICALGRIDSIZE-y); i++){
            for(int j = 0; j<piece[0].length && j<=(HORIZONTALGRIDSIZE- x); j++){
                for(int k = 0; k<piece[0][0].length && k<=(DEPTHSIZE- z); k++){
                    if(piece[i][j][k] == 1){ //for all smaller cubes in the piece, if there is no overlap, place this piece in the grid and
                        if(checkOverlap2(grid, piece, (x-j), (y-i), (z-k), id)) { //if this can happen put it in the arraylist
                            int bruinscore = calculatebruijnscore((x-j), (y-i), (z-k), piece);
                            int[] coordinates1 = new int[7];

                            coordinates1[0] = (x-j);
                            coordinates1[1] = (y-i);
                            coordinates1[2] = (z-k);
                            coordinates1[3] = id;
                            coordinates1[4] = r;
                            coordinates1[5] = bruinscore;
                            coordinates1[6] = 1;
                            coordinates.add(coordinates1);
                        }
                    }
                }
            }
        }
        return coordinates;
    }
    public static int calculatebruijnscore(int x, int y, int z, int[][][] piece) { //calculates the score of the piece
        int bruijnscore = 0;
        for(int i = 0; i<piece.length; i++){
            for(int j = 0; j<piece[0].length; j++){
                for(int k = 0; k<piece[0][0].length; k++){
                    if(piece[i][j][k] == 1){
                        bruijnscore+= 2*(z-k) -5*(y-i)+(x-j); //this is where a genetic algorithm could be implemented later (if we have the time)

                    }
                }
            }
        }
        return bruijnscore;


    }

    //checks if there is overlap if we put our piece on the x,y,z on the grid
    public static boolean checkOverlap2(int[][][] grid, int[][][] piece, int x, int y, int z,int id) {
        for(int i = 0; i<piece.length; i++){
            if (i+y>=VERTICALGRIDSIZE){
                return false;
            }
            for(int j = 0; j<piece[0].length; j++){
                if (x+j>=HORIZONTALGRIDSIZE){
                    return false;
                }
                for(int k = 0; k<piece[0][0].length; k++){
                    if (k+z>=DEPTHSIZE){
                        return false;
                    }
                    if(piece[i][j][k] == 1){
                        if((y+ i) >=0 && (x+j) >=0 && (z+k) >= 0) {
                            if(grid[y+i][x+j][z+k] != -1 && grid[y+i][x+j][z+k] != -100){
                                return false;
                            }
                        }else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    //places the piece in the grid
    public static int [][][] actuallyPlace(int[] coordinates, int[][][] piece, int[][][] localgrid){
        int x = coordinates[0];
        int y = coordinates[1];
        int z = coordinates[2];
        int tempID = coordinates[3];
        for(int i = 0; i<piece.length; i++) {
            for(int j = 0; j<piece[0].length; j++) {
                for(int k = 0; k<piece[0][0].length; k++) {
                    if(piece[i][j][k] == 1) {
                        localgrid[y+i][x+j][z+k] = (tempID+2)*10;

                    }
                }
            }
        }
        return localgrid;
    }
    public static void wa(){//Isnt used yet but might be used later
        try{Thread.sleep(100);}catch(InterruptedException e){System.out.println(e);}
    }

    public static int[] getNextHole(int[][][] grid) { //get the next hole in the grid with y,x,z priority

        for(int y=VERTICALGRIDSIZE-1; y>= 0;y--){
            for(int x=0; x<HORIZONTALGRIDSIZE;x++){
                for (int z=0;z<DEPTHSIZE;z++) {
                    if(grid[y][x][z] == -1) {
                        int[] array = {x, y, z};
                        return array;
                    }
                }
            }
        }
        return null;
    }
    public static int [][][] copy(int [][][] deb){
        int [][][] final_one = new int[VERTICALGRIDSIZE][HORIZONTALGRIDSIZE][DEPTHSIZE];
        for (int i=0;i<deb.length;i++){
            for(int j=0;j<deb[0].length;j++){
                for (int k=0;k<deb[0][0].length;k++){
                    final_one [i][j][k]=deb [i][j][k];
                }
            }
        }

        return final_one;
    }
    public static int[] copy(int[] deb) {
        int[] final_one = new int[deb.length];
        for(int i=0; i<deb.length; i++) {
            final_one[i] = deb[i];


        }
        return final_one;
    }
    public Bruijn(String[] args,boolean pento){
        start =System.currentTimeMillis();
        ab = args;
        pentomino =pento;
        HORIZONTALGRIDSIZE = 5;
        VERTICALGRIDSIZE = 8;
        DEPTHSIZE = 33;
    }
    public Bruijn(String[] args,boolean pento,int ver,int hon,int deep){
        start =System.currentTimeMillis();
        ab = args;
        pentomino =pento;
        HORIZONTALGRIDSIZE = ver;
        VERTICALGRIDSIZE = hon;
        DEPTHSIZE = deep;
    }/*
    public static void main(String[] args){
        ab=args;
        // fit.calculateharder(265,false);
		// fit.ordemax();
		// System.out.println(fit.solutionsetorder.length-1);
        all_poss((0));
    }*/
}