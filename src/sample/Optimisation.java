package sample;

import java.util.ArrayList;

public class Optimisation{
    public static int a;
    public static int b;
    public static int c;
    public static boolean possible = false;
    public static boolean pentomino; 
    public static boolean solution =false;
    public static int [][][] block;
    public static ArrayList <int [][][]> rblock = new ArrayList<int [][][]>();  
    public static reader re; 
	public static int [] nr = new int [3];

    public Optimisation(int a,int b,int c,boolean pentomino){
        this.a =a;
        this.b =b;
        this.c =c;
        // if(a ==0 && c/b==2){
        //     possible =true;
        // }
        this.pentomino =pentomino;
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
    }

    public static int [] assembly(){
        int [] re = new int [3];
        if(possible){
            // System.out.println("x");
            if (a==0){
                if(b==c){
    
                }
                else if(b/c>0){
                    
                }
                else if(c/b>0){
                    re[0]=0;
                    re[1]=1;
                    re[2]=c/b;
                }
            }
            if (b==0){
                if(a==c){
                    
                }
                else if(a/c>0){
    
                }
                else if(a/b>0){
    
                }
            }
        }        
        return re;
    }
    public static void block(){
        int count = 0;
        int [] combi =assembly();
        // System.out.println(combi[1]);
        int [] value =all_poss(combi);
        int volume = volume(value);
        for (int i =volume;i>=1;i--){
            if(volume%i==0){
                int volume2=volume/i;
                for (int y =volume2;y>=1;y--){
                    if(volume2%y==0){
                        count++;
                        int z =volume2/y;
                        int [][][] grid =  new int [i][y][z];
                        solution = false;
                        initializeGrid(grid, all_poss(combi),count);
                    }
                }

            }            
        }     
    }

    public static int volume(int []value){
        int volume =0;
        for (int i =0;i<value.length;i++){
            int [][][] grid =re.data(value[i], 0);
            for(int w =0;w<grid.length;w++){
            	for (int y=0;y<grid[0].length;y++){
            		for (int k=0;k<grid[0][0].length;k++){
            			if (grid[w][y][k]==1){
            				volume++;
            			}
            		}
            	}
            }
        }
        return volume;
    }


    public static void branching(int [] value, int comp, int [][][] grid, int count){
        if (value.length==comp){
            // System.out.println("x");
            solution = true;
            // System.out.println(grid.length);
            // System.out.println(grid[0].length);
            // System.out.println(grid[0][0].length);
            rotation(grid,count);
            return;
        }
        else{
            int [][][] cop=copy(grid);
            int [][][] cop2=copy(cop);
            for (int x =0;x<grid.length;x++){
                for (int y=0;y<grid[0].length;y++){
                    for (int z=0;z<grid[0][0].length;z++){
                        int id = value[comp];
                        for (int r=0;r<nr[id];r++){
                            int [][][] piece = re.data(id,r);					
		    				if (checkOverlap(cop, piece, x, y, z,id)){
		    					id = value[comp];
		    					int [] coordinates ={x,y,z,id};
		    					int [][][] tr2 = copy(cop);
                                int [][][] tr = actuallyPlace(coordinates, piece, tr2);
                                branching(value, comp+1, tr,count);
		    					cop = copy(cop2);
		    					id = value[comp];									
		    				}
                        }
                    }
                }
            }
        }
        
        if (comp==0 && !solution){
            //solution = false;
        }
        // else{
        //     return;
        // }
    }

    public static boolean checkOverlap(int[][][] grid, int[][][] piece, int x, int y, int z,int id) {
		for(int i = 0; i<piece.length; i++){
			if (i+y>=grid.length){
				return false;
			}
			for(int j = 0; j<piece[0].length; j++){
				if (x+j>=grid[0].length){
					return false;
				}
				for(int k = 0; k<piece[0][0].length; k++){
					if (k+z>=grid[0][0  ].length){
						return false;
					}					
					if(piece[i][j][k] == 1){
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
					if(piece[i][j][k] == 1) {
						localgrid[y+i][x+j][z+k] = tempID;
						
					}
				}
			}
		}
		return localgrid;
	}
    

    public static int [][][] copy(int [][][] deb){
        try {
        // System.out.println(deb.length);
		int [][][] final_one = new int[deb.length][deb[0].length][deb[0][0].length];
		for (int i=0;i<deb.length;i++){
			for(int j=0;j<deb[0].length;j++){
				for (int k=0;k<deb[0][0].length;k++){
					final_one [i][j][k]=deb [i][j][k];
				}
			}
		}
		return final_one;
        } catch (Exception e) {
            return null;
        }
        
    }

    public static void initializeGrid(int [][][] grid, int [] value, int count) {
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				for(int k = 0; k<grid[0][0].length; k++) {
					grid[i][j][k] = -1;
				}
			}
        }
        
        // System.out.println("x");
        branching(value, 0, grid,count);
    }

    public static int [] all_poss(int []poss){		
		a = poss [0];
		b = poss [1];
		c = poss [2];
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
        
		return value;				
	}
    public static void rotation(int [][][] block,int count){
        int [][][] wha = copy(block);
        if (wha.length<=Search.VERTICALGRIDSIZE &&
            wha[0].length<=Search.HORIZONTALGRIDSIZE &&
            wha[0][0].length<=Search.DEPTHSIZE){
            boolean check  =false;;
                for (int i=0;i<rblock.size();i++){
                    check =false;
                    int [][][] wha2 = rblock.get(i);
                    if (wha.length == wha2.length &&  wha[0].length ==  wha2[0].length && wha[0][0].length == wha2[0][0].length){
                        check =true;
                    }
                }
                if(!check){
                    rblock.add(wha);
                }           
            }
    }
    /*public static void main(String[] args) {
        Optimisation op = new Optimisation(0, 88, 176, true);
        op.block();
        for (int i=0;i<op.rblock.size();i++){
            int [][][] wha = op.rblock.get(i);
            System.out.println(wha.length);
            System.out.println(wha[0].length);
            System.out.println(wha[0][0].length);            
            System.out.println("x");
        }
        System.out.println(rblock.size());
        // int [][][] wha = rblock.get(0);
        // System.out.println(empty_spot/(wha.length*wha[0].length*wha[0][0].length));        
    }*/

}