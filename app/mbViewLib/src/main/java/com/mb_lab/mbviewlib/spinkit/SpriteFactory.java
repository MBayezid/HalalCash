package com.mb_lab.mbviewlib.spinkit;


import com.mb_lab.mbviewlib.spinkit.sprite.Sprite;
import com.mb_lab.mbviewlib.spinkit.style.ChasingDots;
import com.mb_lab.mbviewlib.spinkit.style.Circle;
import com.mb_lab.mbviewlib.spinkit.style.CubeGrid;
import com.mb_lab.mbviewlib.spinkit.style.DoubleBounce;
import com.mb_lab.mbviewlib.spinkit.style.FadingCircle;
import com.mb_lab.mbviewlib.spinkit.style.FoldingCube;
import com.mb_lab.mbviewlib.spinkit.style.MultiplePulse;
import com.mb_lab.mbviewlib.spinkit.style.MultiplePulseRing;
import com.mb_lab.mbviewlib.spinkit.style.Pulse;
import com.mb_lab.mbviewlib.spinkit.style.PulseRing;
import com.mb_lab.mbviewlib.spinkit.style.RotatingCircle;
import com.mb_lab.mbviewlib.spinkit.style.RotatingPlane;
import com.mb_lab.mbviewlib.spinkit.style.ThreeBounce;
import com.mb_lab.mbviewlib.spinkit.style.WanderingCubes;
import com.mb_lab.mbviewlib.spinkit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
