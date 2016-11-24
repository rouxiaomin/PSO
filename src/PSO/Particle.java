package PSO;
/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
import java.util.Random;  
/** 
 *������ 
 * ��⺯�� f(x)=x1^2+(x2-x3)^2 �����ֵ 
 * @author CX
 */ 
public class Particle 
{
    public double[] pos = new double[dims];//���ӵ�λ�ã�����������ά���������Ϊ����ά   
    public double[] v;//���ӵ��ٶȣ�ά��ͬλ��   
    public double fitness;//���ӵ���Ӧ��   
    public double[] pbest;//���ӵ���ʷ���λ��   
    public static double[] gbest;//���������ҵ������λ��   
    public static Random rnd;  
    public static int dims = 11;  
    public static double w;  
    public static double c1;  
    public static double c2;  
    public double pbest_fitness;//��ʷ���Ž�   
    int[] logo = new int[100]; // ��־λ
    /** 
     * ����low��uper֮����� 
     * @param low ���� 
     * @param uper ���� 
     * @return ����low��uper֮����� 
     */  
    public double m;
    double rand(double low, double uper) {  //����-20��20֮���������
        rnd = new Random();  
        m=rnd.nextDouble() * (uper - low) + low;
        return m;
        //return rnd.nextDouble() * (uper - low) + low;  //NextDouble ���� ����һ������ 0.0 �� 1.0 ֮��������
    }  
    /** 
     * ��ʼ������ 
     * @param dim ��ʾ���ӵ�ά�� 
     */  
    public void initial(int dim,int k) {  
    	
      //  pos = new double[dim];  //����λ��
        v = new double[dim];   //�����ٶ�
        pbest = new double[dim];  //������������λ��
        fitness = -1e6;    //��Ӧֵ
        pbest_fitness = -1e6;  //������Ӧֵ
        dims = dim;  
        for (int i = 0; i < dim; ++i) {  
           // pos[i] = rand(-10, 10);  //������ÿһά��С
            pbest[i] = pos[i];  
            v[i] = rand(-20, 20);  
        }  
    }  
    /** 
     * ��������ֵ,ͬʱ��¼��ʷ����λ�� 
     */ 
    public void UpdateFitness()
    {
     double sum1 = 0;
     double sum2 = 0;
     //����Ackley ������ֵ
     for(int i = 0; i < dims; i++ )
     {
      sum1 += pos[i] * pos[i];
      sum2 += Math.cos(2 * Math.PI * pos[i]);
     }
     //m_dFitness ������ĵ�ǰֵ
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
     * �����ٶȺ�λ�� 
     */  
    public void updatev() { 
	
        for (int i = 0; i < dims; ++i) {  
            v[i] = w * v[i] + c1 * rnd.nextDouble() * (pbest[i] - pos[i])  
                    + c2 * rnd.nextDouble() * (gbest[i] - pos[i])  ;  
           
            pos[i] = pos[i] + v[i];
            
        }          
    } 

}
