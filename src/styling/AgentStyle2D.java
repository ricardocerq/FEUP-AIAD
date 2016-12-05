package styling;

import java.awt.Color;
import java.awt.color.ColorSpace;

import mars.Base;
import mars.Bot;
import mars.EntityGlobals;
import mars.Mineral;
import mars.Producer;
import mars.Spotter;
import mars.Transporter;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public class AgentStyle2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(Object o){
		if (o instanceof Bot){
			Color c;
			if (o instanceof Spotter)
				c = Color.BLUE;
			else if(o instanceof Producer)
				c = Color.YELLOW;
			else
				c = Color.GREEN;
			float[] hsb = Color.RGBtoHSB(c.getRed()/255, c.getGreen()/255, c.getBlue()/255, null);
			hsb[1] = .5f;
			float amount = .25f;
			hsb[2] = (1-amount) + (float) (((Bot)o).getEnergy() / EntityGlobals.getMaxEnergy()) * amount;
			
			return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
			//return c;
		}
		if(o instanceof Base)
			return Color.CYAN;
		return null;
	}
	
	@Override
	public float getScale(Object o) {
		if (o instanceof Bot)
			return 1f;
		if (o instanceof Base)
			return 2f;
		return 1f;
	}
	@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial){
		VSpatial ret = spatial;
		if(agent instanceof Mineral){
			ret = shapeFactory.createRectangle(10, 10);
		} else {
			ret = shapeFactory.createCircle(4, 16);
		}
		return ret;
	}
}