package pl.mn.communicator.gadu;

import pl.mn.communicator.AbstractMessage;

/**
 * Wiadomo�� gg.
 * 
 * @author mnaglik
 */
public final class Message extends AbstractMessage {
	public Message(int toUser, String text) {
		super(toUser,text);
	}
}
