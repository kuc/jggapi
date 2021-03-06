package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.in.GGDisconnecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGDisconnectingPacketHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGDisconnectingPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGDisconnecting packet received.");
		}

		final GGDisconnecting disconnecting = GGDisconnecting.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(disconnecting);
		context.getSessionAccessor().disconnect();
	}

}
