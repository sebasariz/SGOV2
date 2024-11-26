var calendar = {

  init: function() {

    var mon = 'M';
    var tue = 'T';
    var wed = 'W';
    var thur = 'Th';
    var fri = 'F';
    var sat = 'S';
    var sund = 'Su';

    /**
     * Get current date
     */
    var d = new Date();
    var strDate = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate();

    /**
     * Get current month and set as '.current-month' in title
     */
    var monthNumber = d.getMonth() + 1;
    var yearNumber = (new Date).getFullYear();

    function GetMonthName(monthNumber) {
      var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
      return months[monthNumber - 1];
    }

    setMonth(monthNumber, mon, tue, wed, thur, fri, sat, sund);

    function setMonth(monthNumber, mon, tue, wed, thur, fri, sat, sund) {
      $('.calendar-container .month').text(GetMonthName(monthNumber) + ' ' + yearNumber);
      $('.calendar-container .month').attr('data-month', monthNumber);
      printDateNumber(monthNumber, mon, tue, wed, thur, fri, sat, sund);
    }

    $('.calendar-container .btn-next').on('click', function(e) {
      var monthNumber = $('.month').attr('data-month');
      if (monthNumber > 11) {
        $('.month').attr('data-month', '0');
        var monthNumber = $('.month').attr('data-month');
        setMonth(parseInt(monthNumber) + 1, mon, tue, wed, thur, fri, sat, sund);
      } else {
        setMonth(parseInt(monthNumber) + 1, mon, tue, wed, thur, fri, sat, sund);
      };
    });

    $('.calendar-container .btn-prev').on('click', function(e) {
      var monthNumber = $('.month').attr('data-month');
      if (monthNumber < 2) {
        $('.month').attr('data-month', '13');
        var monthNumber = $('.month').attr('data-month');
        setMonth(parseInt(monthNumber) - 1, mon, tue, wed, thur, fri, sat, sund);
      } else {
        setMonth(parseInt(monthNumber) - 1, mon, tue, wed, thur, fri, sat, sund);
      };
    });

    /**
     * Get all dates for current month
     */

    function printDateNumber(monthNumber, mon, tue, wed, thur, fri, sat, sund) {

      $($('tbody.event-calendar tr')).each(function(index) {
        $(this).empty();
      });

      $($('thead.event-days tr')).each(function(index) {
        $(this).empty();
      });

      function getDaysInMonth(month, year) {
        // Since no month has fewer than 28 days
        var date = new Date(year, month, 1);
        var days = [];
        while (date.getMonth() === month) {
          days.push(new Date(date));
          date.setDate(date.getDate() + 1);
        }
        return days;
      }

      var yearNumber = (new Date).getFullYear();
      i = 0;

      setDaysInOrder(mon, tue, wed, thur, fri, sat, sund);

      function setDaysInOrder(mon, tue, wed, thur, fri, sat, sund) {
        var monthDay = getDaysInMonth(monthNumber - 1, yearNumber)[0].toString().substring(0, 3);
        if (monthDay === 'Mon') {
          $('thead.event-days tr').append('<td>' + mon + '</td><td>' + tue + '</td><td>' + wed + '</td><td>' + thur + '</td><td>' + fri + '</td><td>' + sat + '</td><td>' + sund + '</td>');
        } else if (monthDay === 'Tue') {
          $('thead.event-days tr').append('<td>' + tue + '</td><td>' + wed + '</td><td>' + thur + '</td><td>' + fri + '</td><td>' + sat + '</td><td>' + sund + '</td><td>' + mon + '</td>');
        } else if (monthDay === 'Wed') {
          $('thead.event-days tr').append('<td>' + wed + '</td><td>' + thur + '</td><td>' + fri + '</td><td>' + sat + '</td><td>' + sund + '</td><td>' + mon + '</td><td>' + tue + '</td>');
        } else if (monthDay === 'Thu') {
          $('thead.event-days tr').append('<td>' + thur + '</td><td>' + fri + '</td><td>' + sat + '</td><td>' + sund + '</td><td>' + mon + '</td><td>' + tue + '</td><td>' + wed + '</td>');
        } else if (monthDay === 'Fri') {
          $('thead.event-days tr').append('<td>' + fri + '</td><td>' + sat + '</td><td>' + sund + '</td><td>' + mon + '</td><td>' + tue + '</td><td>' + wed + '</td><td>' + thur + '</td>');
        } else if (monthDay === 'Sat') {
          $('thead.event-days tr').append('<td>' + sat + '</td><td>' + sund + '</td><td>' + mon + '</td><td>' + tue + '</td><td>' + wed + '</td><td>' + thur + '</td><td>' + fri + '</td>');
        } else if (monthDay === 'Sun') {
          $('thead.event-days tr').append('<td>' + sund + '</td><td>' + mon + '</td><td>' + tue + '</td><td>' + wed + '</td><td>' + thur + '</td><td>' + fri + '</td><td>' + sat + '</td>');
        }
      };
      $(getDaysInMonth(monthNumber - 1, yearNumber)).each(function(index) {
        var index = index + 1;
        if (index < 8) {
          $('tbody.event-calendar tr.1').append('<td data-month="' + monthNumber + '" data-day="' + index + '">' + index + '</td>');
        } else if (index < 15) {
          $('tbody.event-calendar tr.2').append('<td data-month="' + monthNumber + '" data-day="' + index + '">' + index + '</td>');
        } else if (index < 22) {
          $('tbody.event-calendar tr.3').append('<td data-month="' + monthNumber + '" data-day="' + index + '">' + index + '</td>');
        } else if (index < 29) {
          $('tbody.event-calendar tr.4').append('<td data-month="' + monthNumber + '" data-day="' + index + '">' + index + '</td>');
        } else if (index < 32) {
          $('tbody.event-calendar tr.5').append('<td data-month="' + monthNumber + '" data-day="' + index + '">' + index + '</td>');
        }
        i++;
      });
      var date = new Date();
      var month = date.getMonth() + 1;
      setCurrentDay(month);
      setEvent();
      displayEvent();
    }

    /**
     * Get current day and set as '.current-day'
     */
    function setCurrentDay(month) {
      var viewMonth = $('.month').attr('data-month');
      if (parseInt(month) === parseInt(viewMonth)) {
        $('tbody.event-calendar td[data-day="' + d.getDate() + '"]').addClass('current-day');
      }
    };

    /**
     * Add class '.active' on calendar date
     */
    $('tbody td').on('click', function(e) {
      if ($(this).hasClass('event')) {
        $('tbody.event-calendar td').removeClass('active');
        $(this).addClass('active');
      } else {
        $('tbody.event-calendar td').removeClass('active');
      };
    });

    /**
     * Add '.event' class to all days that has an event
     */
    function setEvent() {
      $('.day-event').each(function(i) {
        var eventMonth = $(this).attr('data-month');
        var eventDay = $(this).attr('data-day');
        $('tbody.event-calendar tr td[data-month="' + eventMonth + '"][data-day="' + eventDay + '"]').addClass('event');
      });
    };

    /**
     * Get current day on click in calendar
     * and find day-event to display
     */
    function displayEvent() {
      $('tbody.event-calendar td').on('click', function(e) {
        var monthEvent = $(this).attr('data-month');
        var dayEvent = $(this).text();
        //$('.day-event[data-month="' + monthEvent + '"][data-day="' + dayEvent + '"]').slideDown('fast');
      });
    };
  },
};

$(document).ready(function() {
  calendar.init();
});