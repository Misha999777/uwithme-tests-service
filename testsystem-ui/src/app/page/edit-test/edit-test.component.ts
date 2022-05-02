import {Component} from '@angular/core';
import {Clipboard} from '@angular/cdk/clipboard';
import {Test} from "../../model/Test";
import {DataService} from "../../service/data.service";
import {Store} from "@ngrx/store";
import {State} from "../../store/admin/admin.reducer";
import {Question} from "../../model/Question";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Observable} from "rxjs";
import {reloadTests, setQuestion} from "../../store/admin/admin.actions";
import {environment} from "../../../environments/environment";

@Component({
    selector: 'testsystem-tests',
    templateUrl: './edit-test.component.html',
    styleUrls: ['./edit-test.component.css']
})
export class EditTestComponent {

    selectedTest: Test = new Test();

    constructor(private store: Store<{admin: State}>,
                private dataService: DataService,
                private router: Router,
                private clipboard: Clipboard,
                private snackBar: MatSnackBar) {
        this.store.select("admin")
            .subscribe(state => this.selectedTest = state.selectedTest ?? new Test());
    }

    createTest() {
        let observable: Observable<Test>;
        if (this.selectedTest.id) {
            observable = this.dataService.updateTest(this.selectedTest);
        } else {
            observable = this.dataService.createTest(this.selectedTest);
        }
        observable.subscribe(() => this.store.dispatch(reloadTests()));
    }

    generateLink() {
        let url = environment.home + "/start?testId=" + this.selectedTest.id;
        this.clipboard.copy(url);
        this.snackBar.open("Ссылка скопирована", null,{duration: 5000});
    }

    deleteTest() {
        this.dataService.deleteTest(this.selectedTest.id)
            .subscribe(() => this.store.dispatch(reloadTests()));
    }

    editQuestion(question: Question) {
        this.router.navigate(["question"])
            .then(() => this.store.dispatch(setQuestion({question: question})));
    }

    deleteQuestion(questionId: number) {
        this.dataService.deleteQuestion(this.selectedTest.id, questionId)
            .subscribe(() => this.store.dispatch(reloadTests()));
    }

    deleteResult(resultId: number) {
        this.dataService.deleteResult(this.selectedTest.id, resultId)
            .subscribe(() => this.store.dispatch(reloadTests()));
    }
}