import {UserRole} from '../../../services/backend/enums';
import {SessionService} from '../../../services/session.service';
import {Footer} from '../containers/footer.container';
import {PrivateHeader} from '../containers/privateHeader.container';
import {PetUpdateForm} from '../forms/petUpdateForm.form';
import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';

export class PetUpdatePageModel {


  constructor() {

  }

}

@Component({
  selector: 'pet-update-page',
  templateUrl: './petUpdatePage.page.html',
  styleUrls: ['./petUpdatePage.page.scss']
})
export class PetUpdatePage implements OnInit, AfterViewInit {
  @Input() id: number;
  @ViewChild(PrivateHeader)
  private readonly headerElement: PrivateHeader;
  @ViewChild(PetUpdateForm)
  private readonly formdasdasElement: PetUpdateForm;
  @ViewChild(Footer)
  private readonly footerElement: Footer;
  model: PetUpdatePageModel = new PetUpdatePageModel();

  constructor(private readonly route: ActivatedRoute, private readonly sessionService: SessionService, private readonly router: Router) {
    if (!([UserRole.VET].includes(sessionService.getSessionData().role)))
      this.router.navigate(['/welcome-page']);
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = + params.id;
    });
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

