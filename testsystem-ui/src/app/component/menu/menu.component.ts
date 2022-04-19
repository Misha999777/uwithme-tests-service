import {Component, Input, OnInit} from '@angular/core';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Test} from "../../model/Test";
import {State} from "../../store/admin/admin.reducer";
import {reloadTests, setTest} from "../../store/admin/admin.actions";
import {AuthServiceFactory} from "../../service/auth.service.factory";
import {UserUtility} from "../../utility/user.utility";

@Component({
    selector: 'testsystem-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    showMenu: boolean;

    tests: Observable<State> = this.store.select("admin");
    mobile: boolean = false;

    constructor(private router: Router,
                private store: Store<{admin: State}>,
                private userUtility: UserUtility) {
        this.showMenu = userUtility.isAdmin();
        this.calculateMobile();
    }

    ngOnInit() {
        if (this.showMenu) {
            this.store.dispatch(reloadTests());
        }
    }

    setTest(test: Test) {
        this.router.navigate(["test"])
            .then(() => this.store.dispatch(setTest({test: test})));
    }

    createTest() {
        this.router.navigate(["test"])
            .then(() => this.store.dispatch(setTest({test: null})));
    }

    private calculateMobile() {
        this.mobile = window.innerWidth <= 800;
        window.onresize = () => this.mobile = window.innerWidth <= 800;
    }
}