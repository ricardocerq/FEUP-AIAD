package styling;

import java.awt.Color;

import mars.Mineral;

public class ScannedEntityStyle2D extends MineralEntityStyle2D{

	@Override
	protected Color mineralColor() {
		return Color.YELLOW;
	}

	@Override
	protected float mineralValue(Mineral m) {
		return m.scannedAmount();
	}

}
