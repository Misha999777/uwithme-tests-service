import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {StudentState} from "../../store/student/student.reducer";

@Component({
    selector: 'testsystem-test-session',
    templateUrl: './test-session.component.html',
    styleUrls: ['./test-session.component.css']
})
export class TestSessionComponent {

    state: StudentState;

    constructor(private store: Store<{student: StudentState}>) {
        this.store.select("student")
            .subscribe(state => this.state = state);
    }
}