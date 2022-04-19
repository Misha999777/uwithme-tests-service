import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {DataService} from "./service/data.service";
import {EditTestComponent} from "./page/edit-test/edit-test.component";
import {MatTableModule} from "@angular/material/table";
import {BrowserModule} from "@angular/platform-browser";
import {MatSidenavModule} from "@angular/material/sidenav";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MenuComponent} from "./component/menu/menu.component";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {AngularEditorModule} from "@kolkov/angular-editor";
import {MatToolbarModule} from "@angular/material/toolbar";
import {EditQuestionComponent} from "./page/edit-question/edit-question.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {StoreModule} from "@ngrx/store";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {ViewResultsComponent} from "./page/view-results/view-results.component";
import {MatRadioModule} from "@angular/material/radio";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {PaginatorComponent} from "./component/paginator/paginator.component";
import {MatChipsModule} from "@angular/material/chips";
import {AuthServiceFactory} from "./service/auth.service.factory";
import {NgxSpinnerModule} from "ngx-spinner";
import {AnimationService} from "./service/animation.service";
import {EffectsModule} from "@ngrx/effects";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {StartComponent} from "./page/start-test/start.component";
import {MatIconModule} from "@angular/material/icon";
import {localStorageSync} from "ngrx-store-localstorage";
import {adminReducer} from "./store/admin/admin.reducer";
import {AdminEffects} from "./store/admin/admin.effects";
import {studentReducer} from "./store/student/student.reducer";
import {QuestionViewerComponent} from "./component/question-viewer/question-viewer.component";
import {CdTimerModule} from "angular-cd-timer";
import {TestSessionComponent} from "./page/test-session/test-session.component";
import {MatDialogModule} from "@angular/material/dialog";
import {RestrictedDialog} from "./page/start-test/restrictred-dialog/restricted.dialog";

@NgModule({
    declarations: [
        AppComponent,
        EditTestComponent,
        MenuComponent,
        EditQuestionComponent,
        ViewResultsComponent,
        PaginatorComponent,
        StartComponent,
        QuestionViewerComponent,
        TestSessionComponent,
        RestrictedDialog
    ],
    imports: [
        StoreModule.forRoot({
            admin: adminReducer,
            student: studentReducer
        }, {
            metaReducers: [
                localStorageSync({
                    keys: ['student'],
                    rehydrate: true,
                })
            ],
        }),
        EffectsModule.forRoot([AdminEffects]),
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        MatTableModule,
        MatSidenavModule,
        MatToolbarModule,
        MatButtonModule,
        MatListModule,
        MatInputModule,
        FormsModule,
        AngularEditorModule,
        MatExpansionModule,
        MatSlideToggleModule,
        MatRadioModule,
        MatCheckboxModule,
        MatChipsModule,
        NgxSpinnerModule,
        MatSnackBarModule,
        MatIconModule,
        CdTimerModule,
        MatDialogModule
    ],
    providers: [
        DataService,
        AuthServiceFactory,
        {provide: HTTP_INTERCEPTORS, useClass: AnimationService, multi: true}
    ],
    bootstrap: [AppComponent]
})

export class AppModule {

}
