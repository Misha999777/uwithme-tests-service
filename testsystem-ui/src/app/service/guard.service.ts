import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {UserUtility} from "../utility/user.utility";
import {StudentState} from "../store/student/student.reducer";
import {State} from "../store/admin/admin.reducer";

@Injectable({
    providedIn: 'root'
})
export class RedirectGuardService implements CanActivate {

    studentState: StudentState;
    adminState: State;

    constructor(private router: Router,
                private userUtility: UserUtility,
                private store: Store<{student: StudentState, admin: State}>) {

        this.store.select("student")
            .subscribe(state => this.studentState = state);
        this.store.select("admin")
            .subscribe(state => this.adminState = state);
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean  {
        if (this.studentState?.testSession) {
            this.redirect(route, "session");
        } else if (!this.userUtility.isAdmin()) {
            this.redirect(route, "start");
        }

        return true;
    }

    private redirect(route: ActivatedRouteSnapshot, where: string) {
        if (route.url[0].path != where) {
            this.router.navigate([where], {queryParams: route.queryParams})
                .then(() => console.log("Navigated user"));
        }
    }
}