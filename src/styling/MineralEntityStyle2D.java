package styling;

import java.awt.Color;

import mars.Mineral;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.VSpatial;

public abstract class MineralEntityStyle2D extends DefaultStyleOGL2D{

	@Override
	public Color getColor(Object o){
		if (o instanceof Mineral){
			Color c = mineralColor();
			float v = mineralValue((Mineral)o);
			if(v > 0)
				return new Color(c.getRed()/255f,c.getGreen()/255f, c.getBlue()/255f , v);
			else return new Color(0,0,0,.25f);
		}
		return null;
	}
	
	@Override
	public float getScale(Object o) {
		if (o instanceof Mineral)
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
	
	protected abstract Color mineralColor();
	
	protected abstract float mineralValue(Mineral m);
	
}
