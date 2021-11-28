package baseball.service;

import baseball.domain.Game;
import baseball.domain.User;
import baseball.utils.Parser;
import baseball.utils.RandomUtils;
import baseball.view.RequestInput;
import baseball.view.SystemMessage;
import camp.nextstep.edu.missionutils.Console;

public class GameService {

    int size;
    Game game;
    User user = new User();

    //TODO: 제출전 sout 제거하기
    public void setGame(int size, int start, int end) {
        this.size = size;
        game = new Game(RandomUtils.getRandomNumbers(size, start, end));
        System.out.println(game.getGameNumbers()[0]+""+ game.getGameNumbers()[1]+""+ game.getGameNumbers()[2]);
    }

    public void playGame() {
        int strike = 0;
        while (strike != 3) {
            play();
            SystemMessage systemMessage = new SystemMessage();
            systemMessage.printScoreMessage(game.getBallCount(), game.getStrikeCount());
            strike = game.getStrikeCount();
        }
    }

    private void play() {
        game.initBaseBall();
        user.setUserNumbers(getUserNumber());
        computeScore();
    }

    private int[] getUserNumber() throws IllegalArgumentException{
        RequestInput.requestInputData();
        String input = Console.readLine();
        Parser parser = new Parser();
        return parser.parseUserInput(input, size);
    }

    private void computeScore() {
        for (int i = 0; i < size; i++) {
            compute(game.getGameNumbers(), user.getUserNumbers(), i);
        }
    }

    private void compute(int[] gameNumber, int[] userNumber, int index) {
        int temp = -1;
        for (int i = 0; i < gameNumber.length; i++) {
            if (gameNumber[i] == userNumber[index]) {
                temp = i;
                break;
            }
        }
        incCount(index, temp);
    }

    private void incCount(int index, int temp) {
        if (temp != index && temp != -1) {
            game.incBallCount();
        }
        if (temp == index) {
            game.incStrikeCount();
        }
    }
}
