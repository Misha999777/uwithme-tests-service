import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Store} from "@ngrx/store";
import {TestSession} from "../../model/TestSession";
import {setSession} from "../../store/student/student.actions";
import {MatDialog} from "@angular/material/dialog";
import {RestrictedDialog} from "./restrictred-dialog/restricted.dialog";
import {StudentState} from "../../store/student/student.reducer";

@Component({
    selector: 'testsystem-start',
    templateUrl: './start.component.html',
    styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

    constructor(private router: Router,
                private dialog: MatDialog,
                private dataService: DataService,
                private store: Store<{student: StudentState}>) {}

    ngOnInit(): void {
        let testId = this.router.url
            .split("=")
            .filter(path => path != "/start")
            .reduce((a, b) => b, null);

        this.processTestId(testId);
    }

    private processTestId(testId: string) {
        if (testId) {
            this.dataService.beginTest(testId)
                .subscribe(testSession => this.startTestSession(testSession));
        } else {
            this.dialog.open(RestrictedDialog, {disableClose: true});
        }
    }

    private startTestSession(testSession: TestSession) {
        this.router.navigate(["session"])
            .then(() => this.store.dispatch(setSession({session: testSession})))
    }
}