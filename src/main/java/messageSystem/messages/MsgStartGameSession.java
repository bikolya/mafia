package messageSystem.messages;

import game.GameMechanics;
import game.Player;
import messageSystem.Address;

import java.util.Map;

public class MsgStartGameSession extends MsgToGameMechanics
{
    public MsgStartGameSession(Address from, Address to, Map<String, Player> players) {
        super(from, to);
    }

    @Override
    public void exec(GameMechanics accountService) {

    }
}