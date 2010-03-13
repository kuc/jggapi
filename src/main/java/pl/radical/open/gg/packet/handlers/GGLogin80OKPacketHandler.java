package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGLogin80OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGLogin80OKPacketHandler implements PacketHandler {

	private static final Logger logger = LoggerFactory.getLogger(GGLogin80OKPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("LoginOK packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGLogin80OK loginOk = GGLogin80OK.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(loginOk);
		context.getSessionAccessor().notifyLoginOK();
	}

}
