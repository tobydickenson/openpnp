/*
 	Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 	
 	This file is part of OpenPnP.
 	
	OpenPnP is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenPnP is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenPnP.  If not, see <http://www.gnu.org/licenses/>.
 	
 	For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.openpnp.LengthUnit;
import org.openpnp.gui.components.reticle.CrosshairReticle;
import org.openpnp.gui.components.reticle.FiducialReticle;
import org.openpnp.gui.components.reticle.RulerReticle;

// TODO: For the time being, since setting a property on the reticle doesn't re-save it we are
// making a redundant call to setReticle on every property update. Fix that somehow.
@SuppressWarnings("serial")
public class CameraViewPopupMenu extends JPopupMenu {
	private CameraView cameraView;
	private JMenu reticleMenu;
	private JMenu reticleOptionsMenu;
	
	public CameraViewPopupMenu(CameraView cameraView) {
		this.cameraView = cameraView;
		
		reticleMenu = createReticleMenu();
		JMenu maxFpsMenu = createMaxFpsMenu();
		JCheckBoxMenuItem calibrationModeCheckMenuItem = new JCheckBoxMenuItem(calibrationModeAction);
		
		add(reticleMenu);
		add(maxFpsMenu);
		add(calibrationModeCheckMenuItem);
		addSeparator();
		add("Cancel");
		
		if (cameraView.getReticle() != null) {
			if (cameraView.getReticle() instanceof CrosshairReticle) {
				setReticleOptionsMenu(createCrosshairReticleOptionsMenu((CrosshairReticle) cameraView.getReticle()));
			}
			else if (cameraView.getReticle() instanceof RulerReticle) {
				setReticleOptionsMenu(createRulerReticleOptionsMenu((RulerReticle) cameraView.getReticle()));
			}
			else if (cameraView.getReticle() instanceof FiducialReticle) {
				setReticleOptionsMenu(createFiducialReticleOptionsMenu((FiducialReticle) cameraView.getReticle()));
			}
		}
	}
	
	private JMenu createMaxFpsMenu() {
		ButtonGroup buttonGroup = new ButtonGroup();
		JMenu menu = new JMenu("Maximum FPS");
		JRadioButtonMenuItem menuItem;
		
		menuItem = new JRadioButtonMenuItem("1");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("5");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("10");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("15");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("24");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("30");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("45");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("60");
		menuItem.addActionListener(maxFpsAction);
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		
		return menu;
	}
	
	private JMenu createReticleMenu() {
		JMenu menu = new JMenu("Reticle");

		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButtonMenuItem menuItem;
		
		menuItem = new JRadioButtonMenuItem(noReticleAction);
		if (cameraView.getReticle() == null) {
			menuItem.setSelected(true);
		}
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem(crosshairReticleAction);
		if (cameraView.getReticle() instanceof CrosshairReticle) {
			menuItem.setSelected(true);
		}
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem(rulerReticleAction);
		if (cameraView.getReticle() instanceof RulerReticle) {
			menuItem.setSelected(true);
		}
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem(fiducialReticleAction);
		if (cameraView.getReticle() instanceof FiducialReticle) {
			menuItem.setSelected(true);
		}
		buttonGroup.add(menuItem);
		menu.add(menuItem);
		
		return menu;
	}
	
	private JMenu createCrosshairReticleOptionsMenu(final CrosshairReticle reticle) {
		JMenu menu = new JMenu("Options");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButtonMenuItem menuItem;
		
		menuItem = new JRadioButtonMenuItem("Red");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.red) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.red);
				cameraView.setReticle(reticle);
			}
		});
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Green");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.green) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.green);
				cameraView.setReticle(reticle);
			}
		});
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Yellow");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.yellow) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.yellow);
				cameraView.setReticle(reticle);
			}
		});
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Blue");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.blue) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.blue);
				cameraView.setReticle(reticle);
			}
		});
		menu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("White");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.white) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.white);
				cameraView.setReticle(reticle);
			}
		});
		menu.add(menuItem);
		
		return menu;
	}
	
	private JMenu createRulerReticleOptionsMenu(final RulerReticle reticle) {
		JMenu menu = new JMenu("Options");
		
		JMenu subMenu;
		JRadioButtonMenuItem menuItem;
		ButtonGroup buttonGroup;
		
		subMenu = new JMenu("Color");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("Red");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.red) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.red);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Green");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.green) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.green);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Yellow");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.yellow) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.yellow);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Blue");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.blue) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.blue);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("White");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.white) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.white);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);
		
		subMenu = new JMenu("Units");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("Millimeters");
		buttonGroup.add(menuItem);
		if (reticle.getUnits() == LengthUnit.Millimeters) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnits(LengthUnit.Millimeters);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Inches");
		buttonGroup.add(menuItem);
		if (reticle.getUnits() == LengthUnit.Inches) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnits(LengthUnit.Inches);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);

		subMenu = new JMenu("Units Per Tick");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("0.1");
		buttonGroup.add(menuItem);
		if (reticle.getUnitsPerTick() == 0.1) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnitsPerTick(0.1);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("0.25");
		buttonGroup.add(menuItem);
		if (reticle.getUnitsPerTick() == 0.25) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnitsPerTick(0.25);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("0.50");
		buttonGroup.add(menuItem);
		if (reticle.getUnitsPerTick() == 0.50) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnitsPerTick(0.50);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("1");
		buttonGroup.add(menuItem);
		if (reticle.getUnitsPerTick() == 1) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnitsPerTick(1);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("10");
		buttonGroup.add(menuItem);
		if (reticle.getUnitsPerTick() == 10) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnitsPerTick(10);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);
		
		return menu;
	}
	
	private JMenu createFiducialReticleOptionsMenu(final FiducialReticle reticle) {
		JMenu menu = new JMenu("Options");
		
		JMenu subMenu;
		JRadioButtonMenuItem menuItem;
		ButtonGroup buttonGroup;
		
		subMenu = new JMenu("Color");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("Red");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.red) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.red);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Green");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.green) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.green);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Yellow");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.yellow) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.yellow);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Blue");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.blue) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.blue);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("White");
		buttonGroup.add(menuItem);
		if (reticle.getColor() == Color.white) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setColor(Color.white);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);
		
		subMenu = new JMenu("Units");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("Millimeters");
		buttonGroup.add(menuItem);
		if (reticle.getUnits() == LengthUnit.Millimeters) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnits(LengthUnit.Millimeters);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Inches");
		buttonGroup.add(menuItem);
		if (reticle.getUnits() == LengthUnit.Inches) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setUnits(LengthUnit.Inches);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);

		subMenu = new JMenu("Shape");
		buttonGroup = new ButtonGroup();
		menuItem = new JRadioButtonMenuItem("Circle");
		buttonGroup.add(menuItem);
		if (reticle.getShape() == FiducialReticle.Shape.Circle) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setShape(FiducialReticle.Shape.Circle);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menuItem = new JRadioButtonMenuItem("Square");
		buttonGroup.add(menuItem);
		if (reticle.getShape() == FiducialReticle.Shape.Square) {
			menuItem.setSelected(true);
		}
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setShape(FiducialReticle.Shape.Square);
				cameraView.setReticle(reticle);
			}
		});
		subMenu.add(menuItem);
		menu.add(subMenu);
		
		JCheckBoxMenuItem chkMenuItem = new JCheckBoxMenuItem("Filled");
		chkMenuItem.setSelected(reticle.isFilled());
		chkMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reticle.setFilled(((JCheckBoxMenuItem)e.getSource()).isSelected());
				cameraView.setReticle(reticle);
			}
		});
		menu.add(chkMenuItem);
		
		JMenuItem inputMenuItem = new JMenuItem("Size");
		inputMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog(cameraView,
						String.format("Enter the size in %s", reticle.getUnits().toString().toLowerCase()),
						reticle.getSize() + "");
				if (result != null) {
					reticle.setSize(Double.valueOf(result));
					cameraView.setReticle(reticle);
				}
			}
		});
		menu.add(inputMenuItem);
		
		return menu;
	}
	
	private void setReticleOptionsMenu(JMenu menu) {
		if (reticleOptionsMenu != null) {
			reticleMenu.remove(reticleMenu.getMenuComponentCount() - 1);
			reticleMenu.remove(reticleMenu.getMenuComponentCount() - 1);
		}
		if (menu != null) {
			reticleMenu.addSeparator();
			reticleMenu.add(menu);
		}
		reticleOptionsMenu = menu;
	}
	
	private Action noReticleAction = new AbstractAction("None") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setReticleOptionsMenu(null);
			cameraView.setReticle(null);
		}
	};
	
	private Action crosshairReticleAction = new AbstractAction("Crosshair") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CrosshairReticle reticle = new CrosshairReticle();
			JMenu optionsMenu = createCrosshairReticleOptionsMenu(reticle);
			setReticleOptionsMenu(optionsMenu);
			cameraView.setReticle(reticle);
		}
	};
	
	private Action rulerReticleAction = new AbstractAction("Ruler") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			RulerReticle reticle = new RulerReticle();
			JMenu optionsMenu = createRulerReticleOptionsMenu(reticle);
			setReticleOptionsMenu(optionsMenu);
			cameraView.setReticle(reticle);
		}
	};
	
	private Action fiducialReticleAction = new AbstractAction("Fiducial") {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			FiducialReticle reticle = new FiducialReticle();
			JMenu optionsMenu = createFiducialReticleOptionsMenu(reticle);
			setReticleOptionsMenu(optionsMenu);
			cameraView.setReticle(reticle);
		}
	};
	
	private Action maxFpsAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int maximumFps = Integer.parseInt(e.getActionCommand());
			cameraView.setMaximumFps(maximumFps);
		}
	};
	
	private Action calibrationModeAction = new AbstractAction("Calibration Mode") {
		@Override
		public void actionPerformed(ActionEvent e) {
			cameraView.setCalibrationMode(((JCheckBoxMenuItem) e.getSource()).isSelected());
		}
	};
}
