package styling;

import java.awt.Color;
import java.awt.Font;

import mars.Base;
import mars.Bot;
import mars.EntityGlobals;
import mars.Mineral;
import mars.Producer;
import mars.Spotter;
import mars.Transporter;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;

public class AgentStyle2D extends DefaultStyleOGL2D {

	@Override
	public Color getColor(Object o) {
		if (o instanceof Bot) {
			Color c;
			if (o instanceof Bot && ((Bot) o).getDisabled())
				c = Color.RED;
			else if (o instanceof Spotter)
				c = Color.BLUE;
			else if (o instanceof Producer)
				c = Color.YELLOW;
			else
				c = Color.GREEN;
			float[] hsb = Color.RGBtoHSB(c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, null);
			hsb[1] = .5f;
			float amount = .25f;
			hsb[2] = (1 - amount) + (float) (((Bot) o).getEnergy() / EntityGlobals.getMaxEnergy()) * amount;

			return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		}
		if (o instanceof Base)
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
	public VSpatial getVSpatial(Object agent, VSpatial spatial) {
		VSpatial ret = spatial;
		if (agent instanceof Mineral) {
			ret = shapeFactory.createRectangle(10, 10);
		} else {
			ret = shapeFactory.createCircle(4, 16);
		}
		return ret;
	}

	public String getLabel(Object o) {
		if (o instanceof Bot) {
			Bot b = (Bot) o;
			String type = "?";
			if (b instanceof Transporter)
				type = "T " + ((Transporter) b).getCarrying() + " ";
			else if (b instanceof Spotter)
				type = "S";
			else if (b instanceof Producer)
				type = "P";

			String state = "" + Math.round(100 * ((b.getEnergy() / EntityGlobals.getMaxEnergy())));

			return type + ", " + state;
		}

		return null;
	}

	@Override
	public Font getLabelFont(Object object) {
		return new Font("Arial", Font.BOLD, 12);
	}

	@Override
	public Position getLabelPosition(Object object) {
		return Position.NORTH;
	}

	@Override
	public Color getLabelColor(Object object) {
		return Color.WHITE;
	}

	@Override
	public float getLabelYOffset(Object object) {
		return 5;
	}
}