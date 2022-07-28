package cum.jesus.jesusclient.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class JumpEvent extends Event {
    private float motion;

    public final float getMotion() {
        return this.motion;
    }

    public final void setMotion(float set) {
        this.motion = set;
    }

    public JumpEvent(float motion) {
        this.motion = motion;
    }
}
