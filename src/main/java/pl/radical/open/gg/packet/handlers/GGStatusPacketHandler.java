package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.in.GGStatus;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@SuppressWarnings("deprecation")
public class GGStatusPacketHandler implements PacketHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GGStatusPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Received GGStatus packet.");
			LOGGER.debug("PacketHeader: " + context.getHeader());
			LOGGER.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}
		final GGStatus status = new GGStatus(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(status);
		final IUser user = status.getUser();
		final IRemoteStatus statusBiz = status.getStatus();
		context.getSessionAccessor().notifyUserChangedStatus(user, statusBiz);
	}

}
