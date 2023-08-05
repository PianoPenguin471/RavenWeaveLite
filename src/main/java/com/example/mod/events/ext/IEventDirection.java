package ravenweave.client.event.ext;

public interface IEventDirection {

    ravenweave.client.event.ext.EventDirection getDirection();

    default boolean isIncoming() {
        return getDirection() == ravenweave.client.event.ext.EventDirection.INCOMING;
    }

    default boolean isOutgoing() {
        return getDirection() == ravenweave.client.event.ext.EventDirection.OUTGOING;
    }

}
