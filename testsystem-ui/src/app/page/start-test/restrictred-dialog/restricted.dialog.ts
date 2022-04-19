import {Component} from "@angular/core";
import {AuthServiceFactory} from "../../../service/auth.service.factory";

@Component({
    selector: 'testsystem-restricted-dialog',
    templateUrl: 'restricted.dialog.html',
})
export class RestrictedDialog {

    constructor(private authServiceFactory: AuthServiceFactory) {
    }

    onExitClicked() {

    }
}