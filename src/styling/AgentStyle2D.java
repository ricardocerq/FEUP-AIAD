package styling;

import java.awt.Color;
import java.awt.Shape;

import mars.Base;
import mars.Bot;
import mars.Mineral;
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
		if(o instanceof Base)
			return Color.CYAN;
		return null;
	}
	
	@Override
	public float getScale(Object o) {
		if (o instanceof Bot)
			return 1f;
		if (o instanceof Base)
			return 1f;
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