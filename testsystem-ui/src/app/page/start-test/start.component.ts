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
    template: ''
})
export class StartComponent implements OnInit {

    private noTestIdMessage = "Якщо ви хочете пройти тест - викладач пивинен надіслати вам посилання.\n" +
        "Лише викладач або адміністратор можуть створити тест.";
    private alreadyWrittenMessage = "Ви вже пройшли цей тест";
    private testCompleted = "Результат теста збережено";

    constructor(private router: Router,
                private route: ActivatedRoute,
                private dialog: MatDialog,
                private dataService: DataService,
                private store: Store<{student: StudentState}>) {}

    ngOnInit(): void {
        let path = this.route.snapshot.url[1];
        if (path?.toString() === "finished") {
            this.openDialog(this.testCompleted);
            return;
        }

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