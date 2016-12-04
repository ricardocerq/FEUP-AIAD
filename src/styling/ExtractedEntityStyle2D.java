package styling;

import java.awt.Color;

import mars.Mineral;

public class ExtractedEntityStyle2D extends MineralEntityStyle2D{

	@Override
	protected Color mineralColor() {
		return Color.GREEN;
	}

	@Override
	protected float mineralValue(Mineral m) {
		return m.extractedAmount();
	}

}
