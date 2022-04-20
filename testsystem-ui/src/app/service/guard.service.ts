import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Store} from "@ngrx/store";
import {UserService} from "./user.service";
import {StudentState} from "../store/student/student.reducer";

@Injectable({
    providedIn: 'root'
})
export class GuardService implements CanActivate {

    studentState: StudentState;

    constructor(private router: Router,
                private userUtility: UserService,
                private store: Store<{student: StudentState}>) {

        this.store.select("student")
            .subscribe(state => this.studentState = state);
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean  {
        if (!this.userUtility.isLoggedIn()) {
            this.redirect(route, "auth");
            return false;
        }

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