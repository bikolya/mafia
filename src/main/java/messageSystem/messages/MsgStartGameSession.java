package messageSystem.messages;

import game.GameMechanics;
import game.Player;
import messageSystem.Address;
import utils.helpers.TimeHelper;

import java.util.Date;
import java.util.Map;

public class MsgStartGameSession extends MsgToGameMechanics {

    private Date start;

    public MsgStartGameSession(Address from, Address to, Map<String, Player> players) {
        super(from, to);
        start = TimeHelper.getTime();
    }

    @Override
    public void exec(GameMechanics accountService) {

    }
}