import {Component, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Test} from "../../model/Test";
import {State} from "../../store/admin/admin.reducer";
import {setTest, setTests} from "../../store/admin/admin.actions";
import {DataService} from "../../service/data.service";

@Component({
    selector: 'testsystem-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    mobile: boolean = false;
    tests: Observable<State> = this.store.select("admin");

    constructor(private router: Router,
                private store: Store<{admin: State}>,
                private dataService: DataService) {
        this.calculateMobile();
    }

    ngOnInit() {
        this.dataService.getTests()
            .subscribe(tests => this.store.dispatch(setTests({tests: tests})));
    }

    setTest(test: Test) {
        this.store.dispatch(setTest({test: test}));
        this.openTests();
    }

    createTest() {
        this.store.dispatch(setTest({test: null}));
        this.openTests();
    }

    private openTests() {
        this.router.navigate(["test"])
            .then(() => console.log("Navigated user"));
    }

    private calculateMobile() {
        this.mobile = window.innerWidth <= 800;
        window.onresize = () => this.mobile = window.innerWidth <= 800;
    }
}