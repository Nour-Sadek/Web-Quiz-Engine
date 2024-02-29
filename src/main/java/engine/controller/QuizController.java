package engine.controller;

import engine.DTO.RequestQuizDTO;
import engine.DTO.ResponseQuizDTO;
import engine.model.Feedback;
import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
public class QuizController {

    private QuizRepository quizRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/api/quizzes")
    public List<ResponseQuizDTO> getAllQuizzes() {
        List<ResponseQuizDTO> output = new ArrayList<>();
        for (Quiz quiz: quizRepository.findAll()) {
            ResponseQuizDTO responseQuiz = new ResponseQuizDTO();
            responseQuiz.loadQuiz(quiz);
            output.add(responseQuiz);
        }
        return output;
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Object> getQuizByID(@PathVariable int id) {
        if (!quizRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Quiz quiz = quizRepository.findById(id).get();
            ResponseQuizDTO responseQuiz = new ResponseQuizDTO();
            responseQuiz.loadQuiz(quiz);
            return new ResponseEntity(responseQuiz, HttpStatus.OK);
        }
    }

    @PostMapping("/api/quizzes")
    public ResponseQuizDTO createNewQuiz(@Valid @RequestBody RequestQuizDTO userQuiz) {

        if (userQuiz.getAnswer() == null) userQuiz.setAnswer(new ArrayList<>());
        Quiz quiz = new Quiz();
        quiz.loadRequestQuizDTO(userQuiz);
        quizRepository.save(quiz);

        ResponseQuizDTO responseQuiz = new ResponseQuizDTO();
        responseQuiz.loadQuiz(quiz);
        return responseQuiz;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Object> getQuizSolution(@RequestBody Map<String, List<Integer>> answer, @PathVariable int id) {
        if (!quizRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Quiz quiz = quizRepository.findById(id).get();
            Feedback feedback = new Feedback();
            // We want to compare the user's answers with the correct way, ignoring list order
            Set<Integer> set1 = new TreeSet<>(answer.get("answer"));
            Set<Integer> set2 = new TreeSet<>(quiz.getAnswer());

            if (set1.equals(set2)) {
                feedback.setSuccess(true);
                feedback.setFeedback(Feedback.CORRECT_FEEDBACK);
            } else {
                feedback.setSuccess(false);
                feedback.setFeedback(Feedback.WRONG_FEEDBACK);
            }
            return new ResponseEntity(feedback, HttpStatus.OK);
        }
    }

}
