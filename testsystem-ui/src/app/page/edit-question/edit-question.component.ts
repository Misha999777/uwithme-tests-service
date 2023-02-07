import {Component} from '@angular/core';
import {Store} from "@ngrx/store"
import {Question} from "../../model/Question";
import {Answer} from "../../model/Answer";
import {DataService} from "../../service/data.service";
import {map, Observable} from "rxjs";
import {Router} from "@angular/router";
import {reloadTests} from "../../store/admin/admin.actions";
import {State} from "../../store/admin/admin.reducer";

@Component({
    selector: 'testsystem-questions',
    templateUrl: './edit-question.component.html',
    styleUrls: ['./edit-question.component.css']
})
export class EditQuestionComponent {

    selectedQuestion: Question = new Question();
    selectedTestId: string;

    constructor(private store: Store<{ admin: State }>,
                private dataService: DataService,
                private router: Router) {
        this.store.select('admin')
            .pipe(map(state => structuredClone(state)))
            .subscribe(state => this.processState(state));
    }

    processState(state: State) {
        this.selectedTestId = state.selectedTest?.id;
        this.selectedQuestion = state.selectedQuestion ?? this.selectedQuestion;

        if (!this.selectedTestId) {
            this.openTests();
        }
    }

    save() {
        let observable: Observable<Question>;

        if (this.selectedQuestion.id) {
            observable = this.dataService.updateQuestion(this.selectedTestId, this.selectedQuestion);
        } else {
            observable = this.dataService.createQuestion(this.selectedTestId, this.selectedQuestion);
        }

        observable.subscribe(() => {
            this.store.dispatch(reloadTests());
            this.openTests();
        })
    }

    answerChanged(newValue: string, correct: boolean, i: number) {
        if (!this.selectedQuestion.answers[i]) {
            this.selectedQuestion.answers[i] = new Answer(newValue, correct);
        } else {
            this.selectedQuestion.answers[i].text = newValue;
            this.selectedQuestion.answers[i].correct = correct;
        }

        this.selectedQuestion.answers = this.selectedQuestion.answers.filter(value => value.text);
    }

    private openTests() {
        this.router.navigate(["test"])
            .then(() => console.log("Navigated user"));
    }
}