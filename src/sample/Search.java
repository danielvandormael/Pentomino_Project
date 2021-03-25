package sample;

import java.util.*;

public class Search {
	public static int [] value;
	public static int HORIZONTALGRIDSIZE = 5;
	public static int VERTICALGRIDSIZE = 8;
	public static int DEPTHSIZE = 33;
	public static int score;
	public static int maxsc =0;
	public static int maxscol =0;
	public static int [] bvalue = {0,0,0,0};
	public static int empty_spot;
	public static long startTime;
	public static long endTime;
	public static int a;
	public static int b;
	public static int cou =0;
	public static int c;
	public static boolean pentomino;
	public static int step;
	public static int witcs;
	public static String [] ab;
	public static int [] nr = new int [4];
	public static int [][][] gridm = new int[VERTICALGRIDSIZE][HORIZONTALGRIDSIZE][DEPTHSIZE];
	public static boolean begin = false;
	public static boolean news = false;
	public static ArrayList <int [][][]> rblock = new ArrayList<int [][][]>();
	public static boolean convert =false;
	public static long start;
	public static long end;

	public static void branching(int[][][] grid, int comp, reader re){
		final int check = comp;
		int id;
		if (checkEnd(grid,comp)){
			if (pentomino){
				end = System.currentTimeMillis();
				System.out.println("Solution found "+step);
				System.out.println("no perfect fit the best score of the box :"+maxsc);
				System.out.println("L :"+bvalue[0]+" T :"+bvalue[1]+" P :"+bvalue[2]+" npiece :"+bvalue[3]);
				System.out.println(empty_spot);
				System.out.println(Math.round((end-start)/1000)+"s");
				gridm =grid.clone();
				UI.repaint(gridm, ab);
				System.exit(0);
			}else{
				System.out.println("Solution found "+step);
				System.out.println("no perfect fit the best score of the box :"+maxsc);
				System.out.println("A :"+bvalue[0]+" B :"+bvalue[1]+" C :"+bvalue[2]+" npiece :"+bvalue[3]);
				System.out.println(empty_spot);
				System.out.println(Math.round((end-start)/1000)+"s");
				gridm =grid.clone();
				UI.repaint(gridm, ab);
				System.exit(0);
			}
		}
		else if(comp<value.length){
			// System.out.println(comp);
			id = value[comp];
			int [][][] cop = copy(grid);
			int [][][] cop2 = copy(cop);
			for(int x=0; x<HORIZONTALGRIDSIZE;x++){
				id = value[comp];
				for(int y=0; y<VERTICALGRIDSIZE;y++){
					id = value[comp];
					for (int z=0;z<DEPTHSIZE;z++){
						id = value[comp];
						for (int r=0;r<nr[id];r++){
							id = value[comp];
							int [][][] piece;
							if (id==3){
								piece = rblock.get(r);
							}
							else{
								piece = re.data(id,r);
							}
							if (checkOverlap(cop, piece, x, y, z,id)){
								step++;
								id = value[comp];
								int [] coordinates ={x,y,z,id};
								int [][][] tr2 = copy(cop);
								int [][][] tr = actuallyPlace(coordinates, piece, tr2);
								if (!checkEnd(cop, comp)){
									scor(comp,tr);
									branching(tr,comp+1,re);
								}
								else{
									System.exit(0);
								}
								cop = copy(cop2);
								id = value[comp];
							}
						}
					}
				}
			}
			if (comp==0 /*|| Optimisation.possible*/){
				if (maxsc!=maxscol){
					if (pentomino){
						System.out.println("no perfect fit the best score of the box :"+maxsc);
						System.out.println("L :"+bvalue[0]+" T :"+bvalue[1]+" P :"+bvalue[2]+" npiece :"+bvalue[3]);
						System.out.println(empty_spot);
						maxscol =maxsc;
						UI.repaint(gridm, ab);
						System.exit(0);
					}
					else{
						System.out.println("no perfect fit the best score of the box :"+maxsc);
						System.out.println("A :"+bvalue[0]+" B :"+bvalue[1]+" C :"+bvalue[2]+" npiece :"+bvalue[3]);
						System.out.println(empty_spot);
						maxscol =maxsc;
						UI.repaint(gridm, ab);
						System.exit(0);
					}
				}else{
					System.out.println("There is no solution");
				}

			}
			else{
				return;
			}
		}
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

	public void all_poss(int []poss){
		if (poss [3]!=0){
			a = poss [0];
			b = poss [1];
			c = poss [2];
			score = poss [3];
			empty_spot =(VERTICALGRIDSIZE*DEPTHSIZE*HORIZONTALGRIDSIZE)- poss[4];
			Optimisation op = new Optimisation(a, b, c, true);
			if (op.possible){
				op.block();
				// op.rotation();
				int [][][] wha = op.rblock.get(0);
				rblock = op.rblock;
				System.out.println(poss[4]/(wha.length*wha[0].length*wha[0][0].length));
				value = new int [poss[4]/(wha.length*wha[0].length*wha[0][0].length)];
				for (int i =0;i<value.length;i++){
					value[i]=3;
				}
				score = value.length*13;
				// System.exit(0);
			}
			else{
				System.out.println("yes");
				int [] value = new int [a+b+c];
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
				this.value = RandomizeArray(value);
			}
			// this.value = RandomizeArray(value);
			initializeGrid();
		}
		else{
			System.out.println("No possible");
		}
	}

	public static int[] RandomizeArray(int[] array){
		if(pentomino){
			Random rgen = new Random();  // Random number generator
			for (int i=0; i<array.length; i++) {
				int randomPosition = rgen.nextInt(array.length);
				int temp = array[i];
				array[i] = array[randomPosition];
				array[randomPosition] = temp;
			}
		}
		return array;
	}

	public static void initializeGrid() {
		//Pieces.initializeDatabase();
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
			nr [3]=rblock.size();
		}
		else{
			re = new reader(10);
			nr[0] =3;
			nr[1]=6;
			nr[2]=1;
			nr[3]=rblock.size();
		}
		branching(grid, 0, re);
	}

	public static boolean checkEnd(int[][][] grid,int p){
//		int pour = score/10;// accuracy
//		if (maxsc > score - pour){
//			return true;
//		}
		if (p!=value.length){
			return false;
		}
		return true;
	}

	public static boolean checkOverlap(int[][][] grid, int[][][] piece, int x, int y, int z,int id) {
		for(int i = 0; i<piece.length; i++){
			if (i+y>=VERTICALGRIDSIZE){
				return false;
			}
			for(int j = 0; j<piece[0].length; j++){
				if (x+j>=HORIZONTALGRIDSIZE){
					return false;
				}
				for(int k = 0; k<piece[0][0].length		; k++){
					if (k+z>=DEPTHSIZE){
						return false;
					}
					if(id!=3){
						if(piece[i][j][k] == 1){
							if(grid[y+i][x+j][z+k] != -1){
								return false;
							}
						}
					}
					else{
						if(grid[y+i][x+j][z+k] != -1){
							return false;
						}
					}

				}
			}
		}
		return true;
	}

	public static int [][][] actuallyPlace(int[] coordinates, int[][][] piece, int[][][] localgrid){
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		int tempID = coordinates[3];

		for(int i = 0; i<piece.length; i++) {
			for(int j = 0; j<piece[0].length; j++) {
				for(int k = 0; k<piece[0][0].length; k++) {
					if(tempID!=3){
						if(piece[i][j][k] == 1) {
							localgrid[y+i][x+j][z+k] = tempID;
						}
					}
					else{
						localgrid[y+i][x+j][z+k] = piece[i][j][k];
					}
				}
			}
		}
		return localgrid;
	}

	public static void wa(){
		try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);}
	}

	public static void scor(int comp,int a[][][]){
		int score =0;
		int hl=0;
		int ht=0;
		int hp=0;
		for (int i=0;i<comp+1;i++){
			if (value[i]==0){
				score+=3;
				hl++;
			}
			else if (value[i]==1){
				if(pentomino){
					score+=5;
				}
				else{
					score+=4;
				}
				ht++;
			}
			else if (value[i]==2){
				if(pentomino){
					score+=4;
				}
				else{
					score+=5;
				}
				hp++;
			}
			else if (value[i]==3){
				score+=13;
				ht++;
				hp+=2;
			}
		}
		maxsc =Math.max(score, maxsc);
		if (maxsc>maxscol){
			bvalue[0]=hl;
			bvalue[1]=ht;
			bvalue[2]=hp;
			bvalue[3]=(comp+1);
			for(int i = 0; i<VERTICALGRIDSIZE; i++){
				for(int j = 0; j<HORIZONTALGRIDSIZE; j++){
					for(int k = 0; k<DEPTHSIZE; k++){
						gridm[i][j][k] = a[i][j][k];
					}
				}
			}
		}
	}

	public Search(String[] args,boolean pento){
		start =System.currentTimeMillis();
		ab = args;
		pentomino =pento;
		HORIZONTALGRIDSIZE = 5;
		VERTICALGRIDSIZE = 8;
		DEPTHSIZE = 33;
	}
	public Search(String[] args,boolean pento,int ver,int hon,int deep){
		start =System.currentTimeMillis();
		ab = args;
		pentomino =pento;
		HORIZONTALGRIDSIZE = ver;
		VERTICALGRIDSIZE = hon;
		DEPTHSIZE = deep;
	}
	// public static void main(String[] args){
	// 	ab =args;
	// 	Fit fit= new Fit();
	// 	fit.calculate(82,false);
	// 	fit.ordemax();
	// 	all_poss((fit.solutionsetorder.length-1),fit);
	// }
}