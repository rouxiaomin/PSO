package PSO;
/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
/** 
 *测试粒子群算法 
 * @author CX
 */ 
public class TestPSO 
{
	int Flags = 0;
	
	static PSO pso = new PSO();
	public static void main(String[] args) 
	{
        PSO pso=new PSO();  
        Particle par = new Particle();
        try {
			pso.readtxt();
		} catch (Exception e1) {
			System.out.print("文件读取成功……");
		}		
        pso.init(491,40); //50 
        pso.plus(40);
        while(pso.flags!=1){
        	
        	 
        	pso.run();
        	pso.plus(40);
        }
        
        try {
			pso.WrietTxt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
