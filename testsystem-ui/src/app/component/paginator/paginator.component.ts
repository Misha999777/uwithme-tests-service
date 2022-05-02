import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {StudentState} from "../../store/student/student.reducer";
import {selectQuestion} from "../../store/student/student.actions";

@Component({
    selector: 'testsystem-paginator',
    templateUrl: './paginator.component.html',
    styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent {

    pages: number;
    currentPage: number;

    constructor(private store: Store<{student: StudentState}>) {
        store.select("student")
            .subscribe(state => this.pages = state.testSession.questions.length)
    }

    setPage(page: number) {
        this.currentPage = page;
        this.store.dispatch(selectQuestion({index: page}));
    }
}