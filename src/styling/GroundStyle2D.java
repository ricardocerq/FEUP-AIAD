package styling;

import java.awt.Color;

import mars.EntityGlobals;
import mars.Mineral;
import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;


public class GroundStyle2D implements ValueLayerStyleOGL {

	protected ValueLayer layer;
	private Color ground = new Color(205, 133, 63);

	public void init(ValueLayer layer) {
		this.layer = layer;
	}

	public float getCellSize() {
		return 15.0f;
	}

	/**
	 * Return the color based on the value at given coordinates.
	 */
	public Color getColor(double... coordinates) {
		/*double v = layer.get(coordinates);
		float[] min = mainColor().getRGBColorComponents(null);
		float[] emp = ground.getRGBColorComponents(null);
		float alpha = ((float)v - EntityGlobals.getMinMineralValue()) / (float)(EntityGlobals.getMaxMineralValue()-EntityGlobals.getMinMineralValue());
		for(int i = 0; i < emp.length; i++){
			emp[i] = emp[i]*(1-alpha) + min[i]*(alpha); 
		}
		return new Color(emp[0],emp[1], emp[2]);*/
		return ground;
	}
	public Color mainColor() {
		return Color.GREEN;
	}
}