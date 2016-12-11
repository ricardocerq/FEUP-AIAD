package styling;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

/**
 * Style for Mineral value layer in 2D display.
 * 
 * @author Eric Tatara
 */
public abstract class Style2D implements ValueLayerStyleOGL {

	protected ValueLayer layer;
	private Color ground = new Color(205, 133, 63);

	public void init(ValueLayer layer) {
		this.layer = layer;
	}

	public float getCellSize() {
		return 15.0f;
	}

	public Color getColor(double... coordinates) {
		return ground;
	}

	public abstract Color mainColor();
}