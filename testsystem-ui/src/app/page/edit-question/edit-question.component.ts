import {Component} from '@angular/core';
import {Store} from "@ngrx/store"
import {Question} from "../../model/Question";
import {Answer} from "../../model/Answer";
import {DataService} from "../../service/data.service";
import {mergeMap, Observable} from "rxjs";
import {Router} from "@angular/router";
import {AngularEditorConfig} from "@kolkov/angular-editor/lib/config";
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

    config: AngularEditorConfig = {
        editable: true,
        minHeight: '20rem',
        maxHeight: '20rem'
    };

    constructor(private store: Store<{admin: State}>,
                private dataService: DataService,
                private router: Router) {
        this.store.select('admin')
            .subscribe(state => this.processState(state));
    }

    processState(state: State) {
        this.selectedTestId = state.selectedTest.id;
        this.selectedQuestion = state.selectedQuestion ?? new Question();
        if (this.selectedTestId) {
            this.router.navigate(["test"])
                .then(() => "Navigated user");
        }
    }

    save() {
        let observable: Observable<Question>;

        if (this.selectedQuestion.id) {
            observable = this.dataService.updateQuestion(this.selectedTestId, this.selectedQuestion);
        } else {
            observable = this.dataService.createQuestion(this.selectedTestId, this.selectedQuestion);
        }

        observable.pipe(mergeMap(() => this.router.navigate(["test"])))
                  .subscribe(() => this.store.dispatch(reloadTests()));
    }

    answerChanged(newValue: string, correct: boolean, i: number) {
        if (!this.selectedQuestion.answers[i]) {
            this.selectedQuestion.answers[i] = new Answer(newValue, correct);
        } else {
            this.selectedQuestion.answers[i].text = newValue;
            this.selectedQuestion.answers[i].correct = correct;
        }

        if (newValue.length == 0) {
            this.selectedQuestion.answers =
                    this.selectedQuestion.answers.filter((value, index) => index != i);
        }
    }
}