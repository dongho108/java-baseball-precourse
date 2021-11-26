package baseball;

import baseball.repository.BaseBall;
import baseball.repository.Number;
import baseball.service.Computer;
import baseball.utils.Parser;
import baseball.utils.RandomUtils;
import baseball.view.RequestInput;
import baseball.view.SystemMessage;

import camp.nextstep.edu.missionutils.Console;

public class Controller {
    final int SIZE = 3;
    final int START_INCLUSIVE = 1;
    final int END_INCLUSIVE = 9;
    final int RETRY = 1;
    final int GAME_OVER = 2;

    Parser parser = new Parser();
    Computer computer = new Computer();
    SystemMessage systemMessage = new SystemMessage();
    Number number = new Number(SIZE);

    public void run() throws IllegalArgumentException{
        gameSet();
        gameStart();
    }

    private void gameSet() {
        number.setGameNumbers(RandomUtils.getRandomNumbers(SIZE, START_INCLUSIVE, END_INCLUSIVE));
    }

    // TODO: 제출전에 println 제거하기
    private void gameStart() throws IllegalArgumentException{
        System.out.println(number.getGameNumbers()[0]+""+number.getGameNumbers()[1]+""+number.getGameNumbers()[2]);
        play();
        gameOver();
    }

    private void play() throws IllegalArgumentException{
        int strike = 0;
        while (strike != 3) {
            number.setUserNumbers(getUserNumber());
            BaseBall baseBall = computer.computeScore(number.getGameNumbers(), number.getUserNumbers(), SIZE);
            systemMessage.outputScoreMessage(baseBall.getBallCount(), baseBall.getStrikeCount());
            strike = baseBall.getStrikeCount();
        }
    }

    private int[] getUserNumber() throws IllegalArgumentException{
        RequestInput.requestInputData();

        String input = Console.readLine();
        int inputNum[] = parser.parseUserInput(input, SIZE);

        return inputNum;
    }

    private void gameOver() throws IllegalArgumentException{
        systemMessage.outputGameOverMessage();
        RequestInput.outputRetryMessage();
        askRetry();
    }

    /**
     * 유저입력이 (문자 or 0 or 3 이상)  : Exception
     * 유저입력이 (1)                  : 재시작
     * 유저입력이 (2)                  : 종료
     */
    private void askRetry() throws IllegalArgumentException{
        String input = Console.readLine();
        int inputNum = Integer.parseInt(input);

        if (inputNum == 0 || inputNum > GAME_OVER) {
            throw new IllegalArgumentException();
        }

        if (inputNum == RETRY) {
            run();
        }
    }
}
