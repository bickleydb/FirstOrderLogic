import java.util.ArrayList;
import java.util.Arrays;

public class WorldGenerator {

  public static World[] createWorlds(Universe uni, Function[] functions) {
	  ArrayList<World> worlds = new ArrayList<World>();
	  ArrayList<Const> constants = uni.getConstants();
	  for(int i = 0; i < functions.length; i++) {
		  int numParams = functions[i].getNumParams();
		  for(int t = 0; t < numParams; t++) {
			  
		  }
	  }
	  
	  
	  
	  return (World[]) worlds.toArray();
  }

}
