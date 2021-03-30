.. java:import:: java.util LinkedHashMap

.. java:import:: java.util Map

.. java:import:: java.util Random

ShopType
========

.. java:package:: scheduler
   :noindex:

.. java:type:: public enum ShopType

   Enumeration for all shop types

Enum Constants
--------------
FLOW
^^^^

.. java:field:: public static final ShopType FLOW
   :outertype: ShopType

JOB
^^^

.. java:field:: public static final ShopType JOB
   :outertype: ShopType

OPEN
^^^^

.. java:field:: public static final ShopType OPEN
   :outertype: ShopType

Methods
-------
getByName
^^^^^^^^^

.. java:method:: public static ShopType getByName(String n)
   :outertype: ShopType

   Reverse lookup the enum instance by its string identifier

   :param n: name of shop type to lookup
   :return: ShopType enum

getName
^^^^^^^

.. java:method:: public String getName()
   :outertype: ShopType

   Method to get the name associated with this shop type

   :return: String name

getRandom
^^^^^^^^^

.. java:method:: public static ShopType getRandom()
   :outertype: ShopType

   Returns a random shop type instance, useful for benchmarking

   :return: ShopType enum

next
^^^^

.. java:method:: public ShopType next()
   :outertype: ShopType

   Method to get the next enum of this instance

   :return: ShopType enum

prev
^^^^

.. java:method:: public ShopType prev()
   :outertype: ShopType

   Method to get the previous enum of this instance

   :return: ShopType enum

