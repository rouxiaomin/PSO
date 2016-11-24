package PSO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
/** 
 *粒子群类 
 * @author CX 
 */ 
public class PSO 
{
	/** 
     * 粒子群 
     */ 	
    Particle[] pars;  
    double global_best;//全局最优解   
    int pcount;//粒子的数量   
    double train[][] = new double[491][Particle.dims];
    double shrehold = -1000000;
    int[] logo = new int[2000]; // 标志位
    int flags = 0;
    double w1 = 0.0001;
    double w2 = -1;
    double w3 = -5;
	/***************************************************************************
	 * 读文件data3.txt中数据，保存至train[][]二维数组中
	 **************************************************************************/
	public void readtxt() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("c:/data.txt"));
		String s;
		int i, j;
		i = 0;
		j = 0;
		while (br.ready())// 判断此流是否已准备好被读取
		{
			s = br.readLine();// 读文件中一行
			java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
					s, ",， ");
			while (tokenizer.hasMoreTokens())// 测试此 tokenizer
			// 的字符串中是否还有更多的可用标记
			{
				train[i][j] = Double.parseDouble(tokenizer.nextToken());// 将读入的字符串转化为双精度型数据
				
				j++;
				if (j == Particle.dims) {
					j = 0;
					i++;
				}
			}
		}
	}

	/** 
     * 粒子群初始化 
     * @param n 粒子的数量 
     */  
    public void init(int n,int k) {  
        pcount = k;  
        global_best = -1e20;  
        int index = -1;  
        pars = new Particle[k];  
        //类的静态成员的初始化   
        Particle.c1 = 2;  
        Particle.c2 = 2;  
        Particle.w = 0.8;  
       // Particle.dims = 3;   //粒子维数  
        int temp = 0;
        for (int i = 0; i < k; i++){  
        	temp = i*(n/k-1);
        	pars[i] = new Particle();
        	// 初始化簇中心
			for (int j = 0; j < Particle.dims; j++) {								
				pars[i].pos[j]= train[temp][j];
				//System.out.println(pars[0].pos[0]+"数据");
			}
			 pars[i].initial(Particle.dims,k);  //3表示粒子维数
			 pars[i].UpdateFitness();  
	            if (global_best < pars[i].pbest_fitness) {  
	                global_best = pars[i].pbest_fitness;  
	                index = i;  
	            }  
        }
		for (int i = 0; i < train.length; i++) {
			logo[i] = 0;// 初始化标志位
		}        
        Particle.gbest = new double[Particle.dims];  
        for (int i = 0; i < 3; ++i) {  
            Particle.gbest[i] = pars[index].pos[i];  
        }  
    }  
    
    /***************************************************************************
	 * 比较差值，确定所属类，计算每条记录和中心点距离
	 **************************************************************************/
	public void plus(int k)// 求方差
	{ 	
		double sum1 = 0;
		double sum2 = 0;
		double sum3 = 0;
		for (int i = 0; i<train.length; i++) {	
			//double [] temp = new double[Particle.dims];
			double min = 0;
			int m = 0;	
			//int index = -1; 
			//double v[] = new double[Particle.dims]; 
			while(m < k){
				double result = 0;
				double result1 = 0;	
				double result2 = 0;	
				double len1 = 0;
				double len2 = 0;
				double elen1 = 0;
				double elen2 = 0;
				int count = 0;	
				int result3 = 0;
			for (int j = 0; j < 3; j++) {			
				result1 += Math.pow((train[i][j] - pars[m].pos[j]), 2);	
				System.out.println(pars[m].pos[j]+"kkkkk"+j);
			}
			for (int j = 3; j < 6; j++) {			
				result2 += train[i][j]*pars[m].pos[j];						
			}
			for (int j = 0; j < Particle.dims; j++) {			
				len1 += Math.pow(train[i][j],2);
				len2 += Math.pow(train[m][j], 2);
			}
			for (int j = 6; j < Particle.dims; j++) {			
				if(train[i][j]==pars[m].pos[j]){
					count++;
				}				
			}
			elen1 = Math.pow(len1, 0.5);
			elen2 = Math.pow(len2, 0.5);			
			if(count > 2){
				result3 = 1;
			}
			sum1 +=Math.pow(result1, 0.5);
			sum2 += result2/(elen1*elen2);	
			sum3 += result3;
			
			result = w1*Math.pow(result1, 0.5)+ w2*result2/elen1*elen2 + w3*result3;
		//	System.out.println(result+"result");
			if(m==0){
				min = result;			
			}			
	
			if(result <= min){
				min = result;
				this.logo[i] = m;
				System.out.println(m+"标志位");


			}			   
		    	m++;     
			}			
		}
		System.out.println(sum1/491+"sum2");
		System.out.println(sum2/491+"sum2");	
		System.out.println(sum3/491+"sum2");
	}

    public void run() {  
        //int runtimes = 500;  
        int index = -1;  
     //   while (runtimes > 0) {  
          
            //每个粒子更新位置和适应值   
            for (int i = 0; i < pcount; ++i) {  
            	if(pars[i].pbest_fitness < shrehold){
                pars[i].updatev();  
                pars[i].UpdateFitness();  
                if (global_best < pars[i].fitness) {  
                    global_best = pars[i].fitness;  
                    index = i;  
                }  
            }else{
            	flags = 1;
            }
            }
            //发现更好的解   
            if (index != -1) {  
                for (int i = 0; i < 3; ++i) {  
                    Particle.gbest[i] = pars[index].pos[i];  
                }  
            }  
            //--runtimes;  
        }  
      
    /***************************************************************************
	 * 写文件到cluster_result.txt中
	 **************************************************************************/
	public void WrietTxt() throws Exception {
		int[]count = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[]num = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39};
		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"c:/cluster_result.txt"));
		int i = 0;
		while (i < train.length) {
			String s = "";
			for (int j = 0; j < Particle.dims; j++) {
				s += "  " + String.valueOf(train[i][j]) + "  ";// 获得train[][]每一行数据，转化为字符串类型
			}
			;
			bw.write(s + String.valueOf(logo[i]));// 加入标志位
		    for (int j : num) {
				if(logo[i]== j){
					count[j]++;
				}
			}
			bw.newLine();
			i++;
		}
		;
		for (int j = 0; j < count.length; j++) {
			System.out.println(count[j]);
		}
		bw.close();
	}
	

}
