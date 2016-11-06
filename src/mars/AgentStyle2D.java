package mars;

import java.awt.Color;
import java.awt.Shape;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.render.PolygonShape;
import saf.v3d.scene.VShape;
import saf.v3d.scene.VSpatial;

/**
 * Style for wolf and sheep in 2D displays.  Use the circle shape 
 * specified in DefaultStyle2D.  Here we change the color and size of the shape
 * depending on if the agent is a Wolf or a Sheep.
 * 
 * @author Eric Tatara
 *
 */
public class AgentStyle2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(Object o){
		if (o instanceof Bot)
			return Color.WHITE;
		if (o instanceof Mineral)
			return Color.BLUE;
		if(o instanceof Base)
			return Color.CYAN;
		return null;
	}
	
	@Override
	public float getScale(Object o) {
		if (o instanceof Bot)
			return 1f;
		if (o instanceof Mineral)
			return 1f;
		if (o instanceof Base)
			return 1f;
		return 1f;
	}
	/*@Override
	public VSpatial getVSpatial(Object agent, VSpatial spatial){
		//if(agent instanceof Mineral)
			//spatial.translate(10, 0, 0);
		VSpatial ret = shapeFactory.createCircle(4, 16);
		if(agent instanceof Bot){
			System.out.println("1");
			ret = shapeFactory.createCircle(4, 16);
		}
		else if(agent instanceof Mineral){
			System.out.println("2");
			ret = shapeFactory.createRectangle(10, 10);
			ret.rotate2D((float)Math.PI/4);
		}
			
		return ret;
	}*/
}