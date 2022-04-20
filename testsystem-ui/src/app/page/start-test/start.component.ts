import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Store} from "@ngrx/store";
import {setSession} from "../../store/student/student.actions";
import {MatDialog} from "@angular/material/dialog";
import {StudentState} from "../../store/student/student.reducer";
import {firstValueFrom} from "rxjs";
import {TestSession} from "../../model/TestSession";
import {DialogComponent} from "../../component/dialog/dialog.component";

@Component({
    selector: 'testsystem-start',
    templateUrl: './start.component.html',
    styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

    private noTestIdMessage = "Если вы хотите пройти тест, попросите у преподавателя ссылку на него.\n" +
        "Вы не можете создать тест, так как не являетесь администратором"
    private alreadyWrittenMessage = "Вы уже писали этот тест"

    constructor(private router: Router,
                private route: ActivatedRoute,
                private dialog: MatDialog,
                private dataService: DataService,
                private store: Store<{student: StudentState}>) {}

    ngOnInit(): void {
        let testId = this.route.snapshot.queryParamMap.get('testId');

        if (testId) {
            this.beginTest(testId);
        } else {
            this.openDialog(this.noTestIdMessage);
        }
    }

    private beginTest(testId: string) {
        firstValueFrom(this.dataService.beginTest(testId))
            .then(testSession => this.openSession(testSession))
            .catch(() => this.openDialog(this.alreadyWrittenMessage));
    }

    private openSession(session: TestSession) {
        this.store.dispatch(setSession({session: session}));
        this.router.navigate(["session"])
            .then(() => console.log("Navigated user"));
    }

    private openDialog(message: string) {
        this.dialog.open(DialogComponent, {
            data: message,
            disableClose: true
        });
    }
}