package cstask5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void startQuiz() {
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
            startTimer();
        } else {
            endQuiz();
        }
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        System.out.println("\nQuestion: " + currentQuestion.getQuestionText());
        List<String> options = currentQuestion.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Your choice (1-" + options.size() + "): ");
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        checkAnswer(userChoice - 1);
    }

    private void checkAnswer(int userChoice) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctOptionIndex = currentQuestion.getCorrectOptionIndex();

        if (userChoice == correctOptionIndex) {
            System.out.println("Correct!\n");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " + (correctOptionIndex + 1) + ". " +
                    currentQuestion.getOptions().get(correctOptionIndex) + "\n");
        }

        currentQuestionIndex++;
        startQuiz();
    }

    private void startTimer() {
        int timeLimitInSeconds = 10; // Change the time limit as needed
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up!");
                checkAnswer(-1); // -1 indicates that the user ran out of time
            }
        }, timeLimitInSeconds * 1000);
    }

    private void endQuiz() {
        System.out.println("\nQuiz Completed!");
        System.out.println("Your Score: " + score + "/" + questions.size());
        displaySummary();
    }

    private void displaySummary() {
        System.out.println("\nSummary:");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int userChoice = i < score ? question.getCorrectOptionIndex() : -1;
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            System.out.println("Your Answer: " + (userChoice + 1) + ". " + question.getOptions().get(userChoice));
            System.out.println("Correct Answer: " + (question.getCorrectOptionIndex() + 1) + ". " +
                    question.getOptions().get(question.getCorrectOptionIndex()) + "\n");
        }
    }
}

class QuizApplication {
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();

        // Example Questions
        List<String> options1 = List.of("Option A", "Option B", "Option C", "Option D");
        Question question1 = new Question("What is the capital of France?", options1, 2);

        List<String> options2 = List.of("10", "20", "30", "40");
        Question question2 = new Question("What is 2 multiplied by 15?", options2, 1);

        List<String> options3 = List.of("Earth", "Mars", "Jupiter", "Venus");
        Question question3 = new Question("Which planet is known as the Red Planet?", options3, 1);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
