package PSO;
/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
import java.util.Random;  
/** 
 *粒子类 
 * 求解函数 f(x)=x1^2+(x2-x3)^2 的最大值 
 * @author CX
 */ 
public class Particle 
{
    public double[] pos = new double[dims];//粒子的位置，求解问题多少维，则此数组为多少维   
    public double[] v;//粒子的速度，维数同位置   
    public double fitness;//粒子的适应度   
    public double[] pbest;//粒子的历史最好位置   
    public static double[] gbest;//所有粒子找到的最好位置   
    public static Random rnd;  
    public static int dims = 11;  
    public static double w;  
    public static double c1;  
    public static double c2;  
    public double pbest_fitness;//历史最优解   
    int[] logo = new int[100]; // 标志位
    /** 
     * 返回low—uper之间的数 
     * @param low 下限 
     * @param uper 上限 
     * @return 返回low—uper之间的数 
     */  
    public double m;
    double rand(double low, double uper) {  //返回-20与20之间的任意数
        rnd = new Random();  
        m=rnd.nextDouble() * (uper - low) + low;
        return m;
        //return rnd.nextDouble() * (uper - low) + low;  //NextDouble 方法 返回一个介于 0.0 和 1.0 之间的随机数
    }  
    /** 
     * 初始化粒子 
     * @param dim 表示粒子的维数 
     */  
    public void initial(int dim,int k) {  
    	
      //  pos = new double[dim];  //粒子位置
        v = new double[dim];   //粒子速度
        pbest = new double[dim];  //粒子自身最优位置
        fitness = -1e6;    //适应值
        pbest_fitness = -1e6;  //最优适应值
        dims = dim;  
        for (int i = 0; i < dim; ++i) {  
           // pos[i] = rand(-10, 10);  //粒子中每一维大小
            pbest[i] = pos[i];  
            v[i] = rand(-20, 20);  
        }  
    }  
    /** 
     * 评估函数值,同时记录历史最优位置 
     */ 
    public void UpdateFitness()
    {
     double sum1 = 0;
     double sum2 = 0;
     //计算Ackley 函数的值
     for(int i = 0; i < dims; i++ )
     {
      sum1 += pos[i] * pos[i];
      sum2 += Math.cos(2 * Math.PI * pos[i]);
     }
     //m_dFitness 计算出的当前值
     fitness = -20 * Math.exp(-0.2 * Math.sqrt((1.0/dims) * sum1)) - Math.exp((1.0/dims) * sum2) + 20 + Math.E;
     if(fitness < pbest_fitness)
     {
    	 pbest_fitness = fitness;
    	 pbest_fitness = -pbest_fitness;
      for(int i = 0; i < dims; i++)
      {
         pbest[i] = pos[i];
      }
     }
    }
   
    /** 
     * 更新速度和位置 
     */  
    public void updatev() { 
	
        for (int i = 0; i < dims; ++i) {  
            v[i] = w * v[i] + c1 * rnd.nextDouble() * (pbest[i] - pos[i])  
                    + c2 * rnd.nextDouble() * (gbest[i] - pos[i])  ;  
           
            pos[i] = pos[i] + v[i];
            
        }          
    } 

}
