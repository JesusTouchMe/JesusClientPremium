package cum.jesus.jesusclient.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class MoveEvent extends Event {
    public double x;

    public double y;

    public double z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }
}
