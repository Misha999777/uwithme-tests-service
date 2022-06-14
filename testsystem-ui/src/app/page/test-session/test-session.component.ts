import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {StudentState} from "../../store/student/student.reducer";
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {TestSession} from "../../model/TestSession";
import {setSession} from "../../store/student/student.actions";

@Component({
    selector: 'testsystem-test-session',
    templateUrl: './test-session.component.html',
    styleUrls: ['./test-session.component.css']
})
export class TestSessionComponent {

    testSession: TestSession;
    leftTime: number;

    constructor(private store: Store<{ student: StudentState }>,
                private dataService: DataService,
                private router: Router) {
        this.store.select("student")
            .subscribe(state => this.processState(state.testSession));
    }

    processState(testSession: TestSession) {
        this.testSession = testSession;
        if (testSession) {
            let elapsedTime = (Date.now() - new Date(testSession.startTime).valueOf()) / 1000;
            this.leftTime = testSession.durationMinutes * 60 - elapsedTime;
        }
    }

    endTest() {
        this.dataService.endTest(this.testSession.testId, this.testSession)
            .subscribe(session => {
                this.store.dispatch(setSession({session: null}));
                this.router.navigate(["start", "finished"])
                    .then(() => console.log("Navigated user"));
            });
    }
}