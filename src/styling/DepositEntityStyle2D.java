package styling;

import java.awt.Color;

import mars.Mineral;

public class DepositEntityStyle2D extends MineralEntityStyle2D {

	@Override
	protected Color mineralColor() {
		return Color.RED;
	}

	@Override
	protected float mineralValue(Mineral m) {
		return m.depositAmount();
	}

}
