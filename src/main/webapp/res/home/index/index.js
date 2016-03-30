
(function(){

    function bannerSwiper(){
        var mySwiper = new Swiper('.banner',{
            autoplay:5000,
            visibilityFullFit : true,
            loop:true,
            pagination: '.banner-pagination',
            //nextButton: '.swiper-button-next',
            //prevButton: '.swiper-button-prev',
            paginationClickable: true,
            preloadImages:false
        });

        var w = $(window).width();
        var slidesPerView = 1;
        var spaceBetween = 10;
        var initialSlide = 0;
        if (w > 700) {
            slidesPerView = 3;
            initialSlide = 1;
            spaceBetween = 30;
        }

        var swiper = new Swiper('#works', {
            pagination: '.work-pagination',
            slidesPerView: slidesPerView,
            centeredSlides: true,
            paginationClickable: true,
            spaceBetween: spaceBetween,
            grabCursor: false,
            initialSlide: initialSlide

    //        pagination: '.work-pagination',
    //        effect: 'coverflow',
    //        grabCursor: true,
    //        centeredSlides: true,
    //        slidesPerView: 'auto',
    //        coverflow: {
    //            rotate: 50,
    //            stretch: 0,
    //            depth: 100,
    //            modifier: 1,
    //            slideShadows : true
    //        }
        });

    }

    $(document).ready(function(){
        bannerSwiper();
    });
})();