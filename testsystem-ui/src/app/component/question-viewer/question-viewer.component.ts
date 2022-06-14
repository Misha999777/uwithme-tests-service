import {Component} from '@angular/core';
import {Question} from "../../model/Question";
import {Store} from "@ngrx/store";
import {StudentState} from "../../store/student/student.reducer";
import {answerQuestion} from "../../store/student/student.actions";

@Component({
    selector: 'testsystem-question-viewer',
    templateUrl: './question-viewer.component.html',
    styleUrls: ['./question-viewer.component.css']
})
export class QuestionViewerComponent {

    selectedQuestion: Question;
    userAnswers: number[];

    constructor(private store: Store<{ student: StudentState }>) {
        this.store.select("student")
            .subscribe(state => this.processState(state))
    }

    selectAnswer(index: number) {
        this.saveAnswer(index);

        this.store.dispatch(answerQuestion({
            questionId: this.selectedQuestion.id,
            answers: this.userAnswers
        }))
    }

    private processState(state: StudentState) {
        let selectedQuestion = state.selectedQuestion;
        let userAnswersByQuestionId = state.testSession.userAnswersByQuestionId ?? new Map<number, number[]>();

        this.userAnswers = Object.getOwnPropertyDescriptor(userAnswersByQuestionId, selectedQuestion.id)?.value ?? [];
        this.selectedQuestion = selectedQuestion;
    }

    private saveAnswer(index: number) {
        if (this.selectedQuestion.isMultipleChoice) {
            if (this.userAnswers.includes(index)) {
                this.userAnswers = this.userAnswers.filter(value => value != index);
            } else {
                this.userAnswers.push(index);
            }
        } else {
            this.userAnswers = [index];
        }
    }
}